package com.example.ai.Service;

import com.example.ai.DTO.Review.ReviewDTO;
import com.example.ai.DTO.Review.UserDailyMealPlanDTO;
import com.example.ai.DTO.Review.UserWeeklyMealPlanDTO;
import com.example.ai.Entity.Meal.FoodMenu;

public interface ReviewService {
    public void createReview(FoodMenu foodMenu);
    public void createDailyReview(UserDailyMealPlanDTO userDailyMealPlanDTO, String userEmail);
    public void createWeeklyReview(UserWeeklyMealPlanDTO userWeeklyMealPlanDTO);
    public ReviewDTO incrementLikes(Long reviewId);
    public ReviewDTO incrementDisLikes(Long reviewId);
}
