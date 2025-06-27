package com.demo.nimn.service.review;

import com.demo.nimn.dto.diet.Response.DailyDietDTO;
import com.demo.nimn.dto.diet.Response.FoodSelectionDTO;
import com.demo.nimn.dto.diet.Response.WeeklyDietDTO;
import com.demo.nimn.dto.review.DailyDietReviewDTO;
import com.demo.nimn.dto.review.ReviewDTO;
import com.demo.nimn.dto.review.ReviewSummaryDTO;
import com.demo.nimn.entity.food.Food;
import com.demo.nimn.entity.review.DailyDietReview;
import com.demo.nimn.entity.review.Review;
import com.demo.nimn.entity.review.ReviewSummary;
import com.demo.nimn.repository.food.FoodRepository;
import com.demo.nimn.repository.review.DailyDietReviewRepository;
import com.demo.nimn.repository.review.ReviewRepository;
import com.demo.nimn.repository.review.ReviewSummaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewSummaryRepository reviewSummaryRepository;
    private final DailyDietReviewRepository dailyDietReviewRepository;
    private final FoodRepository foodRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             ReviewSummaryRepository reviewSummaryRepository,
                             DailyDietReviewRepository dailyDietReviewRepository,
                             FoodRepository foodRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewSummaryRepository = reviewSummaryRepository;
        this.dailyDietReviewRepository = dailyDietReviewRepository;
        this.foodRepository = foodRepository;
    }

    // ReviewSummary 관련
    @Override
    public ReviewSummaryDTO getReviewSummaryByFoodId(Long foodId) {
        ReviewSummary reviewSummary = reviewSummaryRepository.findByFoodId(foodId)
                .orElseThrow(() -> new RuntimeException("ReviewSummary not found"));

        List<Review> completedReviews = reviewSummary.getReviews().stream()
                .filter(review -> review.getRating() != null)
                .collect(Collectors.toList());

        reviewSummary.setReviews(completedReviews);
        return convertToReviewSummaryDTO(reviewSummary);
    }

    @Override
    public List<ReviewSummaryDTO> getAllReviewSummaries() {
        List<ReviewSummary> reviewSummaries = reviewSummaryRepository.findAll();
        return reviewSummaries.stream()
                .map(this::convertToReviewSummaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewSummaryDTO> getReviewSummariesOrderByRating(String sortOrder) {
        List<ReviewSummary> reviewSummaries;
        if ("desc".equalsIgnoreCase(sortOrder)) {
            reviewSummaries = reviewSummaryRepository.findAllOrderByAverageRatingDesc();
        } else {
            reviewSummaries = reviewSummaryRepository.findAllOrderByAverageRatingAsc();
        }
        return reviewSummaries.stream()
                .map(this::convertToReviewSummaryDTO)
                .collect(Collectors.toList());
    }

    private void updateReviewSummaryByFoodMenuId(Long foodMenuId) {
        ReviewSummary summary = reviewSummaryRepository.findByFoodId(foodMenuId)
                .orElseThrow(() -> new RuntimeException("ReviewSummary not found"));
        updateReviewSummaryData(summary);
    }

    private void updateReviewSummaryData(ReviewSummary summary) {
        Long foodMenuId = summary.getFood().getId();
        Double averageRating = reviewRepository.calculateAverageRatingByFoodMenuId(foodMenuId);
        Long totalReviews = reviewRepository.countCompletedReviewsByFoodMenuId(foodMenuId);

        summary.setAverageRating(averageRating != null ? averageRating : 0.0);
        summary.setTotalReviews(totalReviews);

        reviewSummaryRepository.save(summary);
    }

    // DailyDietReview 관련
    @Override
    public void createWeeklyDietReviews(WeeklyDietDTO weeklyDietDTO) {
        for (DailyDietDTO dailyDietDTO : weeklyDietDTO.getDailyDiets()) {
            // 해당 날짜의 음식들로 빈 Review 생성
            List<Review> emptyReviews = new ArrayList<>();

            for (FoodSelectionDTO foodSelection : dailyDietDTO.getMealSelections()) {
                Food food = foodRepository.findById(foodSelection.getFoodMenu().getId())
                        .orElseThrow(() -> new RuntimeException("Food not found: " + foodSelection.getFoodMenu().getId()));

                Review review = Review.builder()
                        .userEmail(foodSelection.getUserEmail())
                        .food(food)
                        .rating(1.0)
                        .comment(null)
                        .build();

                emptyReviews.add(review);
            }

            DailyDietReview dailyReview = DailyDietReview.builder()
                    .userEmail(dailyDietDTO.getUserEmail())
                    .reviewDate(dailyDietDTO.getDate())
                    .reviews(emptyReviews)
                    .build();

            dailyDietReviewRepository.save(dailyReview);
        }
    }

    @Override
    public DailyDietReviewDTO getDailyDietReviewByUserAndDate(String userEmail, LocalDate reviewDate) {
        DailyDietReview dailyReview = dailyDietReviewRepository.findByUserEmailAndReviewDate(userEmail, reviewDate)
                .orElseThrow(() -> new RuntimeException("DailyDietReview not found"));
        return convertToDailyDietReviewDTO(dailyReview);
    }

    @Override
    public List<DailyDietReviewDTO> getDailyDietReviewsByDate(LocalDate reviewDate) {
        List<DailyDietReview> dailyReviews = dailyDietReviewRepository.findByReviewDate(reviewDate);
        return dailyReviews.stream()
                .map(this::convertToDailyDietReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DailyDietReviewDTO updateDailyDietReview(Long id, List<ReviewDTO> reviewDTOs) {
        DailyDietReview dailyReview = dailyDietReviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DailyDietReview not found"));

        List<Review> reviews = reviewDTOs.stream()
                .map(this::convertToReviewEntity)
                .collect(Collectors.toList());

        dailyReview.setReviews(reviews);
        DailyDietReview savedDailyReview = dailyDietReviewRepository.save(dailyReview);

        for(ReviewDTO reviewDTO : reviewDTOs){
            updateReviewSummaryByFoodMenuId(reviewDTO.getFoodId());
        }

        return convertToDailyDietReviewDTO(savedDailyReview);
    }

    // DTO 변환 메서드들
    private ReviewDTO convertToReviewDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .userEmail(review.getUserEmail())
                .foodId(review.getFood().getId())
                .foodName(review.getFood().getName())
                .foodImage(review.getFood().getImage())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }

    private Review convertToReviewEntity(ReviewDTO reviewDTO) {
        Food food = foodRepository.findById(reviewDTO.getFoodId())
                .orElseThrow(() -> new RuntimeException("FoodMenu not found"));

        return Review.builder()
                .id(reviewDTO.getId())
                .userEmail(reviewDTO.getUserEmail())
                .food(food)
                .rating(reviewDTO.getRating())
                .comment(reviewDTO.getComment())
                .createdAt(reviewDTO.getCreatedAt())
                .build();
    }

    private ReviewSummaryDTO convertToReviewSummaryDTO(ReviewSummary reviewSummary) {
        List<ReviewDTO> reviewDTOs = reviewSummary.getReviews().stream()
                .filter(review -> review.getRating() != null)
                .map(this::convertToReviewDTO)
                .collect(Collectors.toList());

        return ReviewSummaryDTO.builder()
                .id(reviewSummary.getId())
                .foodId(reviewSummary.getFood().getId())
                .foodName(reviewSummary.getFood().getName())
                .foodImage(reviewSummary.getFood().getImage())
                .averageRating(reviewSummary.getAverageRating())
                .totalReviews(reviewSummary.getTotalReviews())
                .reviews(reviewDTOs)
                .build();
    }

    private DailyDietReviewDTO convertToDailyDietReviewDTO(DailyDietReview dailyReview) {
        List<ReviewDTO> reviewDTOs = dailyReview.getReviews().stream()
                .map(this::convertToReviewDTO)
                .collect(Collectors.toList());

        return DailyDietReviewDTO.builder()
                .id(dailyReview.getId())
                .userEmail(dailyReview.getUserEmail())
                .reviewDate(dailyReview.getReviewDate())
                .reviews(reviewDTOs)
                .build();
    }
}