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
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_member_lesson",
                        columnNames = {
                                "memberUUID",
                                "lessonId"
                        }
                )
        }
)
public class Review {
/* 리뷰 엔티티 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @Column(nullable = false)
    @Check(constraints = "starScore BETWEEN 1 AND 5")
    private Double starScore;

    @Column(nullable = false)
    private Long lessonId;
    @Column(nullable = false)
    private UUID memberUUID;
    @Column(nullable = false)
    private String nickname;

}
