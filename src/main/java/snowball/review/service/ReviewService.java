package snowball.review.service;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import snowball.review.dto.review.reviewCreateRequest.ReviewCreateRequest;
import snowball.review.dto.review.reviewDeleteResponse.ReviewDeleteResponse;
import snowball.review.dto.review.reviewUpdateRequest.ReviewUpdateRequest;
import snowball.review.exception.GlobalExceptionHandler;
import snowball.review.repository.ReviewRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import snowball.review.review.Review;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 토큰 파싱하는거.. 따로 뺄지 여기둘지 고민
    private SecretKey secretKey; // 객체키 만듬

    public ReviewService(ReviewRepository reviewRepository, @Value("${spring.jwt.secret}")String secret) {
        this.reviewRepository = reviewRepository;
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS512.key().build().getAlgorithm());
    }

    @Transactional
    public Long createReview(ReviewCreateRequest reviewCreateRequest, Long lessonId, String accessToken) {

        Review newReview = new Review();
        newReview.setReviewContent(reviewCreateRequest.getReviewContent());
        newReview.setLessonId(reviewCreateRequest.getLessonId());
        newReview.setStarScore(reviewCreateRequest.getStarScore());

        newReview.setLessonId(lessonId);
        newReview.setMemberUUID(tokenParser(accessToken)); //파싱해야하나?

        reviewRepository.save(newReview);

        return newReview.getReviewId();
    }

    @Transactional // memberId 검증이 필요한가?
    public Long updateReview(ReviewUpdateRequest reviewUpdateRequest, Long reviewId) {

        Review review = reviewRepository.findByReviewId(reviewId)
                .orElseThrow(() -> new GlobalExceptionHandler.ReviewNotFoundException());

        review.setReviewContent(reviewUpdateRequest.getReviewContent());
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


    public UUID tokenParser(String accessToken){

        if (accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey).build()
                    .parseSignedClaims(accessToken)
                    .getPayload();

            // claims.get("memberUUID")가 String을 반환하므로, UUID.fromString()을 사용
            UUID memberId = UUID.fromString((String) claims.getSubject());

            return memberId;

        } catch (JwtException | IllegalArgumentException e){
            throw new JwtException("토큰 오류", e);
        }
    }



}
