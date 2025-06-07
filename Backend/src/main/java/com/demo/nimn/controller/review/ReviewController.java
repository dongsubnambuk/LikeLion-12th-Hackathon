package com.demo.nimn.controller.review;

import com.demo.nimn.dto.review.DailyReviewDTO;
import com.demo.nimn.dto.review.ReviewDTO;
import com.demo.nimn.dto.review.UserWeeklyMealPlanDTO;
import com.demo.nimn.service.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/{date}")
    public List<DailyReviewDTO> readDailyReviewDTOByDate(@PathVariable("date") LocalDate date){
        return reviewService.readDailyReviewDTOByDate(date);
    }

    @PutMapping("/likes/{reviewId}")
    public ReviewDTO incrementLikes(@PathVariable("reviewId") Long reviewId) {
        return reviewService.incrementLikes(reviewId);
    }

    @PutMapping("/disLikes/{reviewId}")
    public ReviewDTO incrementDisLikes(@PathVariable("reviewId") Long reviewId) {
        return reviewService.incrementDisLikes(reviewId);
    }
}