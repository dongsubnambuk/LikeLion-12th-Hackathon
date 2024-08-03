package com.example.ai.DAO;

import com.example.ai.Entity.Meal.FoodMenu;
import com.example.ai.Entity.Review.DailyReview;
import com.example.ai.Entity.Review.Review;

import java.util.List;

public interface ReviewDAO {
    public void createReview(Review review);
    public void createDailyReview(DailyReview dailyReview);
    public List<Review> readReviewsByFoodMenus(List<FoodMenu> foodMenus);
    public Review incrementLikes(Long reviewId);
    public Review incrementDisLikes(Long reviewId);
    public void insertComment(Review review);
}
