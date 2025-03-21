package snowball.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import snowball.review.dto.ApiResponse;
import snowball.review.dto.review.reviewRequest.ReviewCreateRequest;
import snowball.review.dto.review.reviewResponse.ReviewDeleteResponse;
import snowball.review.dto.review.reviewRequest.ReviewUpdateRequest;
import snowball.review.dto.review.reviewResponse.ReviewResponse;
import snowball.review.service.ReviewService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public ApiResponse<List<ReviewResponse>> getReviewListByLessonId(@PathVariable Long lessonId) {
        return ApiResponse.success(reviewService.getReviewListByLessonId(lessonId));
    }


    // review read(R)
    @Operation(summary = "회원ID 하나로 리뷰들 조회")
    @GetMapping("/reviews/member")
    public ApiResponse<List<ReviewResponse>> getReviewListByMemberId(@RequestHeader("Authorization") String token) {
        return ApiResponse.success(reviewService.getReviewListByMemberId(token));
    }

    // review read(R)
    @Operation(summary = "리뷰 벌크 조회")
    @GetMapping("/reviews/bulk")
    public ApiResponse<List<ReviewResponse>> getBulkReviews(
            @Parameter(
                    description = "조회할 리뷰 ID 목록 (쉼표로 구분)",
                    example = "95,110,8,15,23,150,48,51,69",
                    required = true
            )
            @RequestParam("ids") String reviewIds
    ) {
        List<Long> ids = Arrays.stream(reviewIds.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
        return ApiResponse.success(reviewService.getBulkReviews(ids));
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
