package snowball.review.dto.review.reviewResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import snowball.review.review.Review;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ReviewResponse {

    @Schema(description = "레슨 고유키", example = "7")
    private Long reviewId;
    @Schema(description = "리뷰 내용", example = "추천하는 강의입니다.")
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Schema(description = "리뷰 생성일", example = "2025-03-12T05:40:00")
    private LocalDateTime createdAt;
    @Schema(description = "강의 평점", example = "3.5")
    private Double starScore;

    @Schema(description = "강의 고유키", example = "17")
    private Long lessonId;
    @Schema(description = "멤버 고유키", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID memberUUID;
    @Schema(description = "멤버 닉네임", example = "길동이")
    private String nickname;

    public ReviewResponse() {
    }

    public ReviewResponse(Long reviewId, String content, LocalDateTime createdAt, Double starScore, Long lessonId, UUID memberUUID, String nickname) {
        this.reviewId = reviewId;
        this.content = content;
        this.createdAt = createdAt;
        this.starScore = starScore;
        this.lessonId = lessonId;
        this.memberUUID = memberUUID;
        this.nickname = nickname;
    }

    public ReviewResponse(Review review) {
        this.reviewId = review.getReviewId();
        this.content = review.getContent();
        this.createdAt = review.getCreatedAt();
        this.starScore = review.getStarScore();
        this.lessonId = review.getLessonId();
        this.memberUUID = review.getMemberUUID();
        this.nickname = review.getNickname();
    }
}
