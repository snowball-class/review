package snowball.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import snowball.review.dto.ApiResponse;
import snowball.review.dto.review.reviewRequest.ReviewCreateRequest;
import snowball.review.dto.review.reviewResponse.ReviewDeleteResponse;
import snowball.review.dto.review.reviewRequest.ReviewUpdateRequest;
import snowball.review.dto.review.reviewResponse.ReviewGetResponse;
import snowball.review.service.ReviewService;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping
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
    @PostMapping("lesson/{lessonId}/review")
    public ApiResponse<Long> createReview(@RequestBody ReviewCreateRequest reviewCreateRequest,
                                          @PathVariable Long lessonId,
                                          @RequestHeader("Authorization") String token) {
        return ApiResponse.success(reviewService.createReview(reviewCreateRequest, lessonId, token));
    }


    // review read(R)
    @Operation(summary = "레슨ID 하나로 리뷰들 조회")
    @GetMapping("/lesson/reviews/bulk")
    public ApiResponse<List<ReviewGetResponse>> getReviewListByLessonId(@PathVariable Long lessonId) {
        return ApiResponse.success(reviewService.getReviewListByLessonId(lessonId));
    }


    // review read(R)
    @Operation(summary = "회원ID 하나로 리뷰들 조회")
    @GetMapping("/member/reviews/bulk")
    public ApiResponse<List<ReviewGetResponse>> getReviewListByMemberId(@RequestHeader("Authorization") String token) {
        return ApiResponse.success(reviewService.getReviewListByMemberId(token));
    }


    // review update(U)
    @Operation(summary = "리뷰 수정")
    @PutMapping("lesson/{lessonId}/review/{review}")
    public ApiResponse<Long> updateReview(@RequestBody ReviewUpdateRequest reviewUpdateRequest,
                                          @PathVariable Long reviewId) {
        return ApiResponse.success(reviewService.updateReview(reviewUpdateRequest, reviewId));
    }


    // review delete(D)
    @Operation(summary = "리뷰 삭제")
    @DeleteMapping("lesson/{lessonId}/review/{review}")
    public ApiResponse<ReviewDeleteResponse> deleteReview(@PathVariable Long reviewId) {
        return ApiResponse.success(reviewService.deleteReview(reviewId));
    }
}
