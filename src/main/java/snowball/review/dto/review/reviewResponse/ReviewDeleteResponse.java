package snowball.review.dto.review.reviewResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ReviewDeleteResponse {

    @Schema(description = "리뷰 삭제 성공 여부", example = "true")
    private Boolean result;

    public ReviewDeleteResponse(){}
    public ReviewDeleteResponse(Boolean result){
        this.result = result;
    }
}
