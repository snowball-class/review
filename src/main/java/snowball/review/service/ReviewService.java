package snowball.review.service;

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
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberClient memberClient;


    // 로컬 test
//    private static final UUID TEST_MEMBER_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
//    private static final String TEST_NICKNAME = "길동이";

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

        // 로컬 test
//        newReview.setMemberUUID(TEST_MEMBER_UUID);
//        newReview.setNickname(TEST_NICKNAME);

        reviewRepository.save(newReview);

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
    public ReviewDeleteResponse deleteReview(Long lessonId, Long reviewId) {
        if(!reviewRepository.existsById(reviewId)){
            return new ReviewDeleteResponse(false);
        }
        if(!reviewRepository.existsByReviewIdAndLessonId(reviewId, lessonId)){
            return new ReviewDeleteResponse(false);
        }
        reviewRepository.deleteByReviewId(reviewId);
        return new ReviewDeleteResponse(true);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewListByMemberId(String token) {
        MemberInfoResponse response = memberClient.getMemberInfo(token).data();
        List<Review> reviews = reviewRepository.findByMemberUUID(response.getMemberUUID());

        // 로컬 test
//        List<Review> reviews = reviewRepository.findByMemberUUID(TEST_MEMBER_UUID);
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
