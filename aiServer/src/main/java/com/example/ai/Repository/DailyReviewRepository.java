package com.example.ai.Repository;

import com.example.ai.Entity.Review.DailyReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyReviewRepository extends JpaRepository<DailyReview, Long> {
}
