package com.example.ai.Service;

import com.example.ai.DTO.ReviewDTO;
import com.example.ai.Entity.FoodMenu;

public interface ReviewService {
    public void createReview(FoodMenu foodMenu);
    public ReviewDTO incrementLikes(Long reviewId);
    public ReviewDTO incrementDisLikes(Long reviewId);
}
