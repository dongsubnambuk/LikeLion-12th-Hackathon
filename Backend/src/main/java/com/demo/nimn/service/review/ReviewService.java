package com.demo.nimn.service.review;

import com.demo.nimn.dto.review.DailyDietReviewDTO;
import com.demo.nimn.dto.review.ReviewDTO;
import com.demo.nimn.dto.review.ReviewSummaryDTO;
import com.demo.nimn.entity.food.DailyDiet;

import java.time.LocalDate;
import java.util.List;

public interface ReviewService {

    // ReviewSummary 관련
    ReviewSummaryDTO getReviewSummaryByFoodMenuId(Long foodMenuId);
    List<ReviewSummaryDTO> getAllReviewSummaries();
    List<ReviewSummaryDTO> getReviewSummariesOrderByRating(String sortOrder); // "desc" or "asc"

    // DailyDietReview 관련
    void createWeeklyDietReviews(String userEmail, LocalDate startDate, List<DailyDiet> dailyDiets);
    DailyDietReviewDTO getDailyDietReviewByUserAndDate(String userEmail, LocalDate reviewDate);
    List<DailyDietReviewDTO> getDailyDietReviewsByDate(LocalDate reviewDate);
    DailyDietReviewDTO updateDailyDietReview(Long id, List<ReviewDTO> reviews);
}