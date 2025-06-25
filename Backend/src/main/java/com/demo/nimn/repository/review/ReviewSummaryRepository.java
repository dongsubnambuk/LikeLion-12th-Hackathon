package com.demo.nimn.repository.review;

import com.demo.nimn.entity.review.ReviewSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewSummaryRepository extends JpaRepository<ReviewSummary, Long> {

    // 특정 음식의 ReviewSummary 조회
    Optional<ReviewSummary> findByFoodId(Long foodId);

    // 평균 별점 기준 정렬 조회 (높은순)
    @Query("SELECT rs FROM ReviewSummary rs ORDER BY rs.averageRating DESC")
    List<ReviewSummary> findAllOrderByAverageRatingDesc();

    // 평균 별점 기준 정렬 조회 (낮은순)
    @Query("SELECT rs FROM ReviewSummary rs ORDER BY rs.averageRating ASC")
    List<ReviewSummary> findAllOrderByAverageRatingAsc();
}