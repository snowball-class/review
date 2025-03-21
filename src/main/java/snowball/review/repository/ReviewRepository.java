package snowball.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import snowball.review.dto.review.reviewResponse.ReviewResponse;
import snowball.review.review.Review;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReviewId(Long reviewId);
    List<Review> findByMemberUUID(UUID memberUUID);
    List<Review> findByLessonId(Long lessonId);
    List<Review> findByReviewIdIn(List<Long> ids);
}
