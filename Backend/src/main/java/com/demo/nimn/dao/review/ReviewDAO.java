package com.demo.nimn.dao.review;

import com.demo.nimn.entity.meal.FoodMenu;
import com.demo.nimn.entity.review.DailyReview;
import com.demo.nimn.entity.review.Review;

import java.time.LocalDate;
import java.util.List;

public interface ReviewDAO {
    public void createReview(Review review);
    public void createDailyReview(DailyReview dailyReview);
    public List<Review> readReviewsByFoodMenus(List<FoodMenu> foodMenus);
    public List<DailyReview> findByReviewDate(LocalDate reviewDate);
    public Review incrementLikes(Long reviewId);
    public Review incrementDisLikes(Long reviewId);
    public void insertComment(Review review);
}
