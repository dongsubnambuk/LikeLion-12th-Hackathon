package com.example.foodserver.Repository;

import com.example.foodserver.Entity.MealSelectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealSelectionRepository extends JpaRepository<MealSelectionEntity, Long> {
    List<MealSelectionEntity> findByUserId(Long userId);
    List<MealSelectionEntity> findByDailyDietId(Long dailyDietId);
    List<MealSelectionEntity> findByMealTime(String mealTime);
}
