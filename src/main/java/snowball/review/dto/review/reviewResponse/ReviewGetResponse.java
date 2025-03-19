package snowball.review.dto.review.reviewResponse;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewGetResponse {

    @Schema(description = "리뷰 작성자", example = "Jerry")
    String nickname;
    @Schema(description = "별점", example = "4,7")
    Double starScore;
    @Schema(description = "리뷰 내용", example = "13")
    String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    @Schema(description = "리뷰 작성일", example = "2025-03-04T12:30:16")
    LocalDateTime createdAt;

    public ReviewGetResponse(){
    }

    public ReviewGetResponse(String nickname, Double starScore, String content, LocalDateTime createdAt){
        this.nickname = nickname;
        this.starScore = starScore;
        this.content = content;
        this.createdAt = createdAt;
    }

}
