package snowball.review.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowball.review.client.MemberClient;
import snowball.review.dto.ApiResponse;
import snowball.review.dto.member.MemberInfoResponse;
import snowball.review.dto.review.reviewRequest.ReviewCreateRequest;
import snowball.review.dto.review.reviewResponse.ReviewDeleteResponse;
import snowball.review.dto.review.reviewRequest.ReviewUpdateRequest;
import snowball.review.dto.review.reviewResponse.ReviewGetResponse;
import snowball.review.exception.GlobalExceptionHandler;
import snowball.review.repository.ReviewRepository;
import snowball.review.review.Review;

import java.lang.reflect.Member;
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
    public Long createReview(ReviewCreateRequest reviewCreateRequest, Long lessonId, String token) {

        Review newReview = new Review();
        newReview.setContent(reviewCreateRequest.getContent());
        newReview.setLessonId(reviewCreateRequest.getLessonId());
        newReview.setStarScore(reviewCreateRequest.getStarScore());

        newReview.setLessonId(lessonId);

        MemberInfoResponse response = memberClient.getMemberInfo(token).data();
        newReview.setMemberUUID(response.getMemberUUID());

        reviewRepository.save(newReview);

        return newReview.getReviewId();
    }

    @Transactional // memberId 검증이 필요한가?
    public Long updateReview(ReviewUpdateRequest reviewUpdateRequest, Long reviewId) {

        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new GlobalExceptionHandler.ReviewNotFoundException());

        review.setContent(reviewUpdateRequest.getContent());
        review.setStarScore(reviewUpdateRequest.getStarScore());

        return review.getReviewId();
    }

    @Transactional
    public ReviewDeleteResponse deleteReview(Long reviewId) {
        if(!reviewRepository.existsById(reviewId)){
            return new ReviewDeleteResponse(false);
        }
        reviewRepository.deleteByReviewId(reviewId);
        return new ReviewDeleteResponse(true);
    }

    @Transactional(readOnly = true)
    public List<ReviewGetResponse> getReviewListByMemberId(String token) {
        MemberInfoResponse response = memberClient.getMemberInfo(token).data();
        List<ReviewGetResponse> reviews = reviewRepository.findByMemberUUID(response.getMemberUUID());
        return reviews;
    }

    @Transactional(readOnly = true)
    public List<ReviewGetResponse> getReviewListByLessonId(Long lessonId) {
        List<ReviewGetResponse> reviews = reviewRepository.findByLessonId(lessonId);
        return reviews;
    }
}
