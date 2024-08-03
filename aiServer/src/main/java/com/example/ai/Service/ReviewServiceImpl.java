package com.example.ai.Service;

import com.example.ai.DAO.ReviewDAO;
import com.example.ai.DTO.Review.DailyReviewDTO;
import com.example.ai.DTO.Review.ReviewDTO;
import com.example.ai.DTO.Review.UserDailyMealPlanDTO;
import com.example.ai.DTO.Review.UserWeeklyMealPlanDTO;
import com.example.ai.Entity.Meal.FoodMenu;
import com.example.ai.Entity.Review.DailyReview;
import com.example.ai.Entity.Review.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewDAO reviewDAO;
    private final MealService mealService;

    @Autowired
    public ReviewServiceImpl(ReviewDAO reviewDAO,
                             @Lazy MealService mealService) {
        this.reviewDAO = reviewDAO;
        this.mealService = mealService;
    }

    @Override
    public void createReview(FoodMenu foodMenu){
        Review review = toReviewEntity(foodMenu);
        reviewDAO.createReview(review);
    }

    @Override
    public void createDailyReview(UserDailyMealPlanDTO userDailyMealPlanDTO, String userEmail){
        List<FoodMenu> foodMenus = new ArrayList<>();

        for(Long foodMenuId : userDailyMealPlanDTO.getFoodMenuIds()){
            FoodMenu foodMenu = mealService.readFoodMenuByFoodMenuId(foodMenuId);
            foodMenus.add(foodMenu);
        }

        List<Review> reviews = reviewDAO.readReviewsByFoodMenus(foodMenus);
        DailyReview dailyReview = toDailyReviewEntity(reviews, userEmail, userDailyMealPlanDTO.getDate());
        reviewDAO.createDailyReview(dailyReview);
    }

    @Override
    public void createWeeklyReview(UserWeeklyMealPlanDTO userWeeklyMealPlanDTO){
        for(UserDailyMealPlanDTO userDailyMealPlanDTO : userWeeklyMealPlanDTO.getUserDailyMealPlans()){
            createDailyReview(userDailyMealPlanDTO, userWeeklyMealPlanDTO.getUserEmail());
        }
    }

    @Override
    public ReviewDTO incrementLikes(Long reviewId){
        Review review = reviewDAO.incrementLikes(reviewId);
        return toReviewDTO(review);
    }

    @Override
    public ReviewDTO incrementDisLikes(Long reviewId){
        Review review = reviewDAO.incrementDisLikes(reviewId);
        return toReviewDTO(review);
    }

    public ReviewDTO toReviewDTO(Review review){
        return ReviewDTO.builder()
                .reviewId(review.getReviewId())
                .likes(review.getLikes())
                .disLikes(review.getDisLikes())
                .comment(review.getComment())
                .build();
    }

    public Review toReviewEntity(FoodMenu foodMenu){
        return Review.builder()
                .foodMenu(foodMenu)
                .likes(Long.valueOf(0))
                .disLikes(Long.valueOf(0))
                .build();
    }

    public DailyReview toDailyReviewEntity(List<Review> reviews, String userEmail, LocalDate date){
        return DailyReview.builder()
                .reviewDate(date)
                .userEmail(userEmail)
                .reviews(reviews)
                .build();
    }
}