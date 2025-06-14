package com.demo.nimn.service.review;

import com.demo.nimn.dao.review.ReviewDAO;
import com.demo.nimn.dto.review.DailyReviewDTO;
import com.demo.nimn.dto.review.ReviewDTO;
import com.demo.nimn.dto.review.UserDailyMealPlanDTO;
import com.demo.nimn.dto.review.UserWeeklyMealPlanDTO;
import com.demo.nimn.entity.meal.FoodMenu;
import com.demo.nimn.entity.review.DailyReview;
import com.demo.nimn.entity.review.Review;
import com.demo.nimn.service.meal.MealService;
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
        // TODO-jh: Food 생성할 때 Review도 같이 생성해서 저장 로직 추가, Cascade 속성 사용 중
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

    // TODO-jh: 사용자 ID, 날짜로 리뷰 생성하는 메소드 구현해야 함.

    public ReviewDTO toReviewDTO(Review review){
        return ReviewDTO.builder()
                .reviewId(review.getId())
                .foodImage(review.getFoodMenu().getImage())
                .foodName(review.getFoodMenu().getName())
                .likes(review.getLikes())
                .disLikes(review.getDisLikes())
                .comment(review.getComments())
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
                .dailyReviewId(dailyReview.getId())
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