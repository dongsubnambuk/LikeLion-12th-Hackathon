package com.demo.nimn.repository.review;

import com.demo.nimn.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 특정 음식의 평균 별점 계산
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.food.id = :foodMenuId AND r.rating IS NOT NULL")
    Double calculateAverageRatingByFoodMenuId(@Param("foodMenuId") Long foodMenuId);

    // 특정 음식의 총 리뷰 개수
    @Query("SELECT COUNT(r) FROM Review r WHERE r.food.id = :foodMenuId AND r.rating IS NOT NULL")
    Long countCompletedReviewsByFoodMenuId(@Param("foodMenuId") Long foodMenuId);
}