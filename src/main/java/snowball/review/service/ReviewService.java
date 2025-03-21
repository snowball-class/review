package snowball.review.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowball.review.client.MemberClient;
import snowball.review.dto.member.MemberInfoResponse;
import snowball.review.dto.review.reviewRequest.ReviewCreateRequest;
import snowball.review.dto.review.reviewResponse.ReviewDeleteResponse;
import snowball.review.dto.review.reviewRequest.ReviewUpdateRequest;
import snowball.review.dto.review.reviewResponse.ReviewResponse;
import snowball.review.exception.GlobalExceptionHandler;
import snowball.review.repository.ReviewRepository;
import snowball.review.review.Review;

import java.util.List;
import java.util.stream.Collectors;

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
            throw new GlobalExceptionHandler.DuplicateReviewException("이미 해당 수업에 대한 리뷰가 존재합니다.");
        }
        return newReview.getReviewId();
    }

    @Transactional
    public Long updateReview(ReviewUpdateRequest reviewUpdateRequest, Long reviewId) {

        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new GlobalExceptionHandler.ReviewNotFoundException("해당하는 리뷰가 없습니다."));

        review.setContent(reviewUpdateRequest.getContent());
        review.setStarScore(reviewUpdateRequest.getStarScore());

        return review.getReviewId();
    }

    @Transactional
    public ReviewDeleteResponse deleteReview(Long reviewId) {

        // deleteById는 데이터 없을 때도 exception 발생하지 않음
        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new GlobalExceptionHandler.ReviewNotFoundException("해당하는 리뷰가 없습니다."));

        reviewRepository.delete(review);

        return new ReviewDeleteResponse(true);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewListByMemberId(String token) {

        MemberInfoResponse response = memberClient.getMemberInfo(token).data();
        List<ReviewResponse> reviews = reviewRepository.findByMemberUUID(response.getMemberUUID()).stream()
                .map(review -> new ReviewResponse(review))
                .collect(Collectors.toList());

        if(reviews.isEmpty()){
            throw new GlobalExceptionHandler.ReviewNotFoundException("해당하는 리뷰가 없습니다.");
        }

        return reviews;
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewListByLessonId(Long lessonId) {

//        List<Review> reviews = reviewRepository.findByLessonId(lessonId);
        List<ReviewResponse> reviews = reviewRepository.findByLessonId(lessonId).stream()
                .map(review -> new ReviewResponse(review))
                .collect(Collectors.toList());

        if(reviews.isEmpty()){
            throw new GlobalExceptionHandler.ReviewNotFoundException("해당하는 리뷰가 없습니다.");
        }
        return reviews;
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getBulkReviews(List<Long> reviewIds) {

        List<ReviewResponse> reviews = reviewRepository.findByReviewIdIn(reviewIds).stream()
                .map(review -> new ReviewResponse(review))
                .collect(Collectors.toList());

        return reviews;
    }
}
