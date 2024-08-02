package com.example.ai.DAO;

import com.example.ai.Entity.Review;
import com.example.ai.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewDAOImpl implements ReviewDAO{
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewDAOImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void createReview(Review review) {
        reviewRepository.save(review);
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
