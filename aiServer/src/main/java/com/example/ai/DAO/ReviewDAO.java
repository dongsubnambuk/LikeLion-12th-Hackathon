package com.example.ai.DAO;

import com.example.ai.Entity.Review;

public interface ReviewDAO {
    public void createReview(Review review);
    public Review incrementLikes(Long reviewId);
    public Review incrementDisLikes(Long reviewId);
    public void insertComment(Review review);
}
