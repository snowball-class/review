package snowball.review.review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Review {
/* 리뷰 엔티티 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reviewContent;
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createAt;
    @Column(nullable = false)
    @Check(constraints = "star_rating BETWEEN 1 AND 5")
    private Double starScore;

    @Column(nullable = false)
    private Long LessonId;
    @Column(nullable = false)
    private UUID memberUUID;

}
