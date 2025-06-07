package com.demo.nimn.service.review;

import com.demo.nimn.dto.review.DailyReviewDTO;
import com.demo.nimn.dto.review.ReviewDTO;
import com.demo.nimn.dto.review.UserDailyMealPlanDTO;
import com.demo.nimn.dto.review.UserWeeklyMealPlanDTO;
import com.demo.nimn.entity.meal.FoodMenu;

import java.time.LocalDate;
import java.util.List;

public interface ReviewService {
    public void createReview(FoodMenu foodMenu);
    public void createDailyReview(UserDailyMealPlanDTO userDailyMealPlanDTO, String userEmail);
    public void createWeeklyReview(UserWeeklyMealPlanDTO userWeeklyMealPlanDTO);
    public List<DailyReviewDTO> readDailyReviewDTOByDate(LocalDate date);
    public ReviewDTO incrementLikes(Long reviewId);
    public ReviewDTO incrementDisLikes(Long reviewId);
}