package com.example.foodserver.Service;

import com.example.foodserver.DTO.DailyDietDTO;
import com.example.foodserver.DTO.MealSelectionDTO;

import java.util.List;
import java.util.Optional;

public interface MealSelectionService {
    MealSelectionDTO create(MealSelectionDTO mealSelectionDTO);
    Optional<MealSelectionDTO> getById(Long mealSelectionId);
    List<MealSelectionDTO> getAll();
    List<MealSelectionDTO> getByUserId(Long userId);
    List<MealSelectionDTO> getByDailyDietId(Long dailyDietId);
    List<MealSelectionDTO> getByMealTime(String mealTime);
    MealSelectionDTO update(Long mealSelectionId, MealSelectionDTO mealSelectionDTO);
    void delete(Long mealSelectionId);

    List<DailyDietDTO> createWeeklyDiet(Long userId, List<DailyDietDTO> dailyDiets);
}
