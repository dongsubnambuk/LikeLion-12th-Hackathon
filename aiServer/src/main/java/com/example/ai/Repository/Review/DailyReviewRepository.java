package com.example.ai.Repository.Review;

import com.example.ai.Entity.Review.DailyReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyReviewRepository extends JpaRepository<DailyReview, Long> {
    List<DailyReview> findByReviewDate(LocalDate reviewDate);
}
