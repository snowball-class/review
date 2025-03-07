package snowball.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import snowball.review.review.Review;

import java.util.Optional;


public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByReviewId(Long reviewId);
    void deleteByReviewId(Long reviewId);
}
