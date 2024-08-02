package com.example.ai.Service;

import com.example.ai.DAO.ReviewDAO;
import com.example.ai.DTO.ReviewDTO;
import com.example.ai.Entity.FoodMenu;
import com.example.ai.Entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewDAO reviewDAO;

    @Autowired
    public ReviewServiceImpl(ReviewDAO reviewDAO) {
        this.reviewDAO = reviewDAO;
    }

    @Override
    public void createReview(FoodMenu foodMenu){
        Review review = toReviewEntity(foodMenu);
        reviewDAO.createReview(review);
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
}