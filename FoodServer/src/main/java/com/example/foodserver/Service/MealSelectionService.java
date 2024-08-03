package com.example.foodserver.Service;

import com.example.foodserver.DTO.MealSelectionDTO;
import java.util.List;
import java.util.Optional;

public interface MealSelectionService {
    MealSelectionDTO createMealSelection(MealSelectionDTO mealSelectionDTO);
    Optional<MealSelectionDTO> getMealSelectionById(Long id);
    List<MealSelectionDTO> getMealSelectionsByUserId(Long userId);
    List<MealSelectionDTO> getMealSelectionsByDailyDietId(Long dailyDietId);
    List<MealSelectionDTO> getMealSelectionsByMealTime(String mealTime);
    MealSelectionDTO updateMealSelection(Long id, MealSelectionDTO mealSelectionDTO);
    void deleteMealSelection(Long id);
}
