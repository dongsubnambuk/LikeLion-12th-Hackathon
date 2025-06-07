package com.demo.nimn.service.review;

import com.example.ai.DTO.Review.DailyReviewDTO;
import com.example.ai.DTO.Review.ReviewDTO;
import com.example.ai.DTO.Review.UserDailyMealPlanDTO;
import com.example.ai.DTO.Review.UserWeeklyMealPlanDTO;
import com.example.ai.Entity.Meal.FoodMenu;

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