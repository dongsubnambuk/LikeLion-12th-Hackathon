package com.demo.nimn.controller.review;

import com.demo.nimn.dto.review.DailyDietReviewDTO;
import com.demo.nimn.dto.review.ReviewDTO;
import com.demo.nimn.dto.review.ReviewSummaryDTO;
import com.demo.nimn.service.review.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "REVIEW API", description = "식단 리뷰 및 평가 관리")
@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // ReviewSummary 관련 API
    @Operation(summary = "음식별 종합 리뷰 조회", description = "특정 음식의 종합 리뷰(평균 별점, 총 리뷰 수 등)를 조회합니다")
    @GetMapping("/summary/{foodMenuId}")
    public ResponseEntity<ReviewSummaryDTO> getReviewSummary(@PathVariable Long foodMenuId) {
        ReviewSummaryDTO reviewSummary = reviewService.getReviewSummaryByFoodMenuId(foodMenuId);
        return ResponseEntity.ok(reviewSummary);
    }

    @Operation(summary = "전체 종합 리뷰 조회", description = "모든 음식의 종합 리뷰를 조회합니다")
    @GetMapping("/summary")
    public ResponseEntity<List<ReviewSummaryDTO>> getAllReviewSummaries() {
        List<ReviewSummaryDTO> reviewSummaries = reviewService.getAllReviewSummaries();
        return ResponseEntity.ok(reviewSummaries);
    }

    @Operation(summary = "별점별 종합 리뷰 조회", description = "별점 기준으로 정렬된 종합 리뷰를 조회합니다")
    @GetMapping("/summary/sort")
    public ResponseEntity<List<ReviewSummaryDTO>> getReviewSummariesSorted(
            @RequestParam(value = "order", defaultValue = "desc") String sortOrder) {
        List<ReviewSummaryDTO> reviewSummaries = reviewService.getReviewSummariesOrderByRating(sortOrder);
        return ResponseEntity.ok(reviewSummaries);
    }

    // DailyDietReview 관련 API
    @Operation(summary = "사용자 특정 날짜 식단 리뷰 조회", description = "특정 사용자의 특정 날짜 식단 리뷰를 조회합니다")
    @GetMapping("/daily")
    public ResponseEntity<DailyDietReviewDTO> getDailyDietReviewByUserAndDate(
            @RequestParam String userEmail,
            @RequestParam LocalDate reviewDate) {
        DailyDietReviewDTO dailyReview = reviewService.getDailyDietReviewByUserAndDate(userEmail, reviewDate);
        return ResponseEntity.ok(dailyReview);
    }

    @Operation(summary = "하루 식단 리뷰 수정", description = "하루 식단 리뷰에 개별 리뷰들을 추가/수정합니다")
    @PutMapping("/daily/{dailyReviewId}")
    public ResponseEntity<DailyDietReviewDTO> updateDailyDietReview(
            @PathVariable Long dailyReviewId,
            @RequestBody List<ReviewDTO> reviews) {
        DailyDietReviewDTO updatedReview = reviewService.updateDailyDietReview(dailyReviewId, reviews);
        return ResponseEntity.ok(updatedReview);
    }
}