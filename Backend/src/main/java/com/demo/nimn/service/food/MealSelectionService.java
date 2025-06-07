package com.demo.nimn.service.food;

import com.demo.nimn.dto.food.Response.MealSelectionDTO;
import com.demo.nimn.dto.food.Request.MealSelectionRequestDTO;
import com.demo.nimn.entity.food.MealSelection;

import java.util.List;

public interface MealSelectionService {
    MealSelectionDTO getById(Long mealSelectionId);
    List<MealSelection> convertToMealSelectionEntities(List<MealSelectionRequestDTO> mealSelectionDTOS);
    List<MealSelectionDTO> convertToMealSelectionDTOS(List<MealSelection> mealSelectionEntities);
}