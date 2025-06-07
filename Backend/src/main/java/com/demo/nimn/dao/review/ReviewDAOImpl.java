package com.demo.nimn.dao.review;

import com.demo.nimn.entity.meal.FoodMenu;
import com.demo.nimn.entity.review.DailyReview;
import com.demo.nimn.entity.review.Review;
import com.demo.nimn.repository.review.DailyReviewRepository;
import com.demo.nimn.repository.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ReviewDAOImpl implements ReviewDAO{
    private final ReviewRepository reviewRepository;
    private final DailyReviewRepository dailyReviewRepository;

    @Autowired
    public ReviewDAOImpl(ReviewRepository reviewRepository,
                         DailyReviewRepository dailyReviewRepository) {
        this.reviewRepository = reviewRepository;
        this.dailyReviewRepository = dailyReviewRepository;
    }

    @Override
    public void createReview(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public void createDailyReview(DailyReview dailyReview) {
        dailyReviewRepository.save(dailyReview);
    }

    @Override
    public List<Review> readReviewsByFoodMenus(List<FoodMenu> foodMenus) {
        return reviewRepository.findByFoodMenuIn(foodMenus);
    }

    @Override
    public List<DailyReview> findByReviewDate(LocalDate reviewDate) {
        return dailyReviewRepository.findByReviewDate(reviewDate);
    }

    @Override
    public Review incrementLikes(Long reviewId) {
        Review review = reviewRepository.getReferenceById(reviewId);
        review.incrementLikes();
        reviewRepository.save(review);
        return review;
    }

    @Override
    public Review incrementDisLikes(Long reviewId) {
        Review review = reviewRepository.getReferenceById(reviewId);
        review.incrementDisLikes();
        reviewRepository.save(review);
        return review;
    }

    @Override
    public void insertComment(Review review) {
        reviewRepository.save(review);
    }
}
