package snowball.review.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowball.review.client.MemberClient;
import snowball.review.dto.member.MemberInfoResponse;
import snowball.review.dto.review.reviewRequest.ReviewCreateRequest;
import snowball.review.dto.review.reviewResponse.ReviewDeleteResponse;
import snowball.review.dto.review.reviewRequest.ReviewUpdateRequest;
import snowball.review.exception.GlobalExceptionHandler;
import snowball.review.repository.ReviewRepository;
import snowball.review.review.Review;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberClient memberClient;

    public ReviewService(ReviewRepository reviewRepository, MemberClient memberClient) {
        this.reviewRepository = reviewRepository;
        this.memberClient = memberClient;
    }

    @Transactional
    public Long createReview(ReviewCreateRequest reviewCreateRequest, String token) {

        Review newReview = new Review();
        newReview.setContent(reviewCreateRequest.getContent());
        newReview.setLessonId(reviewCreateRequest.getLessonId());
        newReview.setStarScore(reviewCreateRequest.getStarScore());

        MemberInfoResponse response = memberClient.getMemberInfo(token).data();
        newReview.setMemberUUID(response.getMemberUUID());
        newReview.setNickname(response.getNickname());

        try{
            reviewRepository.save(newReview);
        } catch (DataIntegrityViolationException e){
            throw new GlobalExceptionHandler.DuplicateReviewException();
        }
        return newReview.getReviewId();
    }

    @Transactional
    public Long updateReview(ReviewUpdateRequest reviewUpdateRequest, Long reviewId) {

        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new GlobalExceptionHandler.ReviewNotFoundException());

        review.setContent(reviewUpdateRequest.getContent());
        review.setStarScore(reviewUpdateRequest.getStarScore());

        return review.getReviewId();
    }

    @Transactional
    public ReviewDeleteResponse deleteReview(Long reviewId) {

        // deleteById는 데이터 없을 때도 exception 발생하지 않음
        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new GlobalExceptionHandler.ReviewNotFoundException());

        reviewRepository.delete(review);

        return new ReviewDeleteResponse(true);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewListByMemberId(String token) {

        MemberInfoResponse response = memberClient.getMemberInfo(token).data();
        List<Review> reviews = reviewRepository.findByMemberUUID(response.getMemberUUID());

        if(reviews.isEmpty()){
            throw new GlobalExceptionHandler.ReviewNotFoundException();
        }
        return reviews;
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewListByLessonId(Long lessonId) {

        List<Review> reviews = reviewRepository.findByLessonId(lessonId);
        if(reviews.isEmpty()){
            throw new GlobalExceptionHandler.ReviewNotFoundException();
        }
        return reviews;
    }
}
