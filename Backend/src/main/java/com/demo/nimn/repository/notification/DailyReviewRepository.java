package com.demo.nimn.repository.notification;

import com.example.notificationserver.Entity.DailyReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface DailyReviewRepository extends JpaRepository<DailyReviewEntity, Long> {
    void deleteByUserEmail(String userEmail);
}
