package snowball.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import snowball.review.dto.ApiResponse;
import snowball.review.dto.review.reviewCreateRequest.ReviewCreateRequest;
import snowball.review.dto.review.reviewDeleteResponse.ReviewDeleteResponse;
import snowball.review.dto.review.reviewUpdateRequest.ReviewUpdateRequest;
import snowball.review.service.ReviewService;

@Controller
@ResponseBody
@RequestMapping("/lessons")
public class ReviewController {
//    리뷰 CRUD
//    회원의 리뷰 조회
//    강의의 리뷰 조회
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // review insert(C)
    @Operation(summary = "리뷰 생성")
    @PostMapping("/{lessonId}/reviews")
    public ApiResponse<Long> createReview(@RequestBody ReviewCreateRequest reviewCreateRequest,
                                          @PathVariable Long lessonId,
                                          @RequestHeader("Authorization") String accessToken) {
        return ApiResponse.success(reviewService.createReview(reviewCreateRequest, lessonId, accessToken));
    }
    

//    // review read(R) : 레슨 ID로 리뷰 bulk 조회
//    @Operation(summary = "레슨 강의 ID 하나로 리뷰 벌크 조회")
//    @GetMapping("/{lessonId}/reviews/bulk")
//    public ApiResponse<List<reviewGetResponse>> getReviewListByLesson(@PathVariable Long lessonId) {
//        return ApiResponse.success(reviewService.getReviewListByLesson(lessonId));
//    }

//    // review read(R) : 회원 ID로 리뷰 bulk 조회
//    @GetMapping("/{lessonId}/reviews")
//    public ApiResponseEntity getReviewListByMember(@RequestBody reviewCreateRequest reviewCreateRequest) {
//        return ApiResponseEntity.success(reviewService.getReviewListByMember(reviewCreateRequest));
//    }
//

    // review update(U)
    @Operation(summary = "리뷰 수정")
    @PutMapping("/{lessonId}/reviews/{reviews}")
    public ApiResponse<Long> updateReview(@RequestBody ReviewUpdateRequest reviewUpdateRequest,
                                          @PathVariable Long reviewId,
                                          @RequestHeader("Authorization") String accessToken) {
        return ApiResponse.success(reviewService.updateReview(reviewUpdateRequest, reviewId, accessToken));
    }


    // review delete(D)
    @Operation(summary = "리뷰 삭제")
    @DeleteMapping("/{lessonId}/reviews/{reviews}")
    public ApiResponse<ReviewDeleteResponse> deleteReview(@PathVariable Long reviewId,
                                                          @RequestHeader("Authorization") String accessToken) {
        return ApiResponse.success(reviewService.deleteReview(reviewId, accessToken));
    }
}
