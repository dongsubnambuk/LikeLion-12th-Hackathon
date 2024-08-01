package com.example.foodserver.DAO;

import com.example.foodserver.DTO.MealSelectionDTO;

import java.util.List;
import java.util.Optional;

public interface MealSelectionDAO {
    MealSelectionDTO create(MealSelectionDTO mealSelectionDTO);
    Optional<MealSelectionDTO> getById(Long id);

    List<MealSelectionDTO> getAll();

    List<MealSelectionDTO> getByUserId(Long userId);
    List<MealSelectionDTO> getByDailyDietId(Long dailyDietId);
    List<MealSelectionDTO> getByMealTime(String mealTime);
    MealSelectionDTO update(Long id, MealSelectionDTO mealSelectionDTO);
    void delete(Long id);
}
