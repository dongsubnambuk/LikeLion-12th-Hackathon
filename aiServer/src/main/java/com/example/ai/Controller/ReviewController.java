package com.example.ai.Controller;

import com.example.ai.DTO.Review.UserWeeklyMealPlanDTO;
import com.example.ai.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meal/review")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping()
    public String createReview(@RequestBody UserWeeklyMealPlanDTO userWeeklyMealPlanDTO) {
        reviewService.createWeeklyReview(userWeeklyMealPlanDTO);
        return "success";
    }
}