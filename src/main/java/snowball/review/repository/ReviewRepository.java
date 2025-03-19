package snowball.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import snowball.review.dto.review.reviewResponse.ReviewGetResponse;
import snowball.review.review.Review;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReviewId(Long reviewId);
    void deleteByReviewId(Long reviewId);
    List<ReviewGetResponse> findByMemberUUID(UUID memberUUID);
    List<ReviewGetResponse> findByLessonId(Long lessonId);
}
