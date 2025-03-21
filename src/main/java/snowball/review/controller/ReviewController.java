package snowball.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import snowball.review.dto.ApiResponse;
import snowball.review.dto.review.reviewRequest.ReviewCreateRequest;
import snowball.review.dto.review.reviewResponse.ReviewDeleteResponse;
import snowball.review.dto.review.reviewRequest.ReviewUpdateRequest;
import snowball.review.review.Review;
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
    @PostMapping("/review")
    public ApiResponse<Long> createReview(@Valid @RequestBody ReviewCreateRequest reviewCreateRequest,
                                          @RequestHeader("Authorization") String token) {
        return ApiResponse.success(reviewService.createReview(reviewCreateRequest, token));
    }


    // review read(R)
    @Operation(summary = "레슨ID 하나로 리뷰들 조회")
    @GetMapping("/reviews/lessons/{lessonId}")
    public ApiResponse<List<Review>> getReviewListByLessonId(@PathVariable Long lessonId) {
        return ApiResponse.success(reviewService.getReviewListByLessonId(lessonId));
    }


    // review read(R)
    @Operation(summary = "회원ID 하나로 리뷰들 조회")
    @GetMapping("/review/member")
    public ApiResponse<List<Review>> getReviewListByMemberId(@RequestHeader("Authorization") String token) {
        return ApiResponse.success(reviewService.getReviewListByMemberId(token));
    }


    // review update(U)
    @Operation(summary = "리뷰 수정")
    @PutMapping("/review/{reviewId}")
    public ApiResponse<Long> updateReview(@Valid @RequestBody ReviewUpdateRequest reviewUpdateRequest,
                                          @PathVariable("reviewId") Long reviewId) {
        return ApiResponse.success(reviewService.updateReview(reviewUpdateRequest, reviewId));
    }


    // review delete(D)
    @Operation(summary = "리뷰 삭제")
    @DeleteMapping("/review/{reviewId}")
    public ApiResponse<ReviewDeleteResponse> deleteReview(@PathVariable("reviewId") Long reviewId) {
        return ApiResponse.success(reviewService.deleteReview(reviewId));
    }
}
