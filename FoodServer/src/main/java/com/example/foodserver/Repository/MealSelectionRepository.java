package com.example.foodserver.Repository;

import com.example.foodserver.Entity.MealSelectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealSelectionRepository extends JpaRepository<MealSelectionEntity, Long> {
    List<MealSelectionEntity> findByUserId(Long userId);
    List<MealSelectionEntity> findByDailyDietId(Long dailyDietId);
    List<MealSelectionEntity> findByMealTime(String mealTime);
}
