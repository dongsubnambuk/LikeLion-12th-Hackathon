package com.demo.nimn.service.review;

import com.example.ai.DAO.Review.ReviewDAO;
import com.example.ai.DTO.Review.DailyReviewDTO;
import com.example.ai.DTO.Review.ReviewDTO;
import com.example.ai.DTO.Review.UserDailyMealPlanDTO;
import com.example.ai.DTO.Review.UserWeeklyMealPlanDTO;
import com.example.ai.Entity.Meal.FoodMenu;
import com.example.ai.Entity.Review.DailyReview;
import com.example.ai.Entity.Review.Review;
import com.example.ai.Service.Meal.MealService;
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
    public List<DailyReviewDTO> readDailyReviewDTOByDate(LocalDate date){
        List<DailyReview> dailyReviews = reviewDAO.findByReviewDate(date);
        return toDailyReviewDTOS(dailyReviews);
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
                .foodImage(review.getFoodMenu().getImage())
                .foodName(review.getFoodMenu().getName())
                .likes(review.getLikes())
                .disLikes(review.getDisLikes())
                .comment(review.getComment())
                .build();
    }

    public List<ReviewDTO> reviewDTOS(List<Review> reviews){
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for(Review review : reviews){
            reviewDTOS.add(toReviewDTO(review));
        }
        return reviewDTOS;
    }

    public DailyReviewDTO toDailyReviewDTO(DailyReview dailyReview){
        return DailyReviewDTO.builder()
                .dailyReviewId(dailyReview.getDailyReviewId())
                .userEmail(dailyReview.getUserEmail())
                .reviewDate(dailyReview.getReviewDate())
                .reviews(reviewDTOS(dailyReview.getReviews()))
                .build();
    }

    public List<DailyReviewDTO> toDailyReviewDTOS(List<DailyReview> dailyReviews){
        List<DailyReviewDTO> dailyReviewDTOS = new ArrayList<>();
        for(DailyReview dailyReview : dailyReviews){
            dailyReviewDTOS.add(toDailyReviewDTO(dailyReview));
        }
        return dailyReviewDTOS;
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