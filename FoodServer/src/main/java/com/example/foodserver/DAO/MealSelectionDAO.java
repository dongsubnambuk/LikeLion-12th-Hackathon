package com.example.foodserver.DAO;

import com.example.foodserver.Entity.MealSelectionEntity;

import java.util.List;
import java.util.Optional;

public interface MealSelectionDAO {
    MealSelectionEntity create(MealSelectionEntity mealSelectionEntity);
    Optional<MealSelectionEntity> getById(Long mealSelectionId);
    List<MealSelectionEntity> getAll();
    List<MealSelectionEntity> getByUserId(Long userId);
    List<MealSelectionEntity> getByDailyDietId(Long dailyDietId);
    List<MealSelectionEntity> getByMealTime(String mealTime);
    MealSelectionEntity update(Long mealSelectionId, MealSelectionEntity mealSelectionEntity);
    void delete(Long mealSelectionId);
}
