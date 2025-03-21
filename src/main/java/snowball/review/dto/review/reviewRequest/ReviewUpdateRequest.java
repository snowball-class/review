package snowball.review.dto.review.reviewRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateRequest {

    @NotBlank(message = "리뷰를 입력해 주세요.")
    @Size(min = 2, max = 1000, message = "1000자 이하로만 입력 가능합니다.")
    @Schema(description = "리뷰내용", example = "강의 후기남깁니다. 최고의 강의입니다.")
    private String content;

    @NotNull(message = "별점을 입력해 주세요.")
    @Min(value = 0, message = "별점은 0점 미만으로 줄 수 없습니다.")
    @Max(value = 5, message = "별점은 5점 초과로 줄 수 없습니다.")
    @Schema(description = "별점", example = "1, 1.5, 2, 2.5, ..., 5")
    private Double starScore;

}
