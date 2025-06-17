package com.demo.nimn.repository.review;

import com.demo.nimn.entity.review.DailyDietReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyDietReviewRepository extends JpaRepository<DailyDietReview, Long> {

    // 특정 사용자의 특정 날짜 DailyDietReview 조회
    Optional<DailyDietReview> findByUserEmailAndReviewDate(String userEmail, LocalDate reviewDate);

    // 특정 날짜의 모든 DailyDietReview 조회
    List<DailyDietReview> findByReviewDate(LocalDate reviewDate);
}