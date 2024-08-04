package com.example.foodserver.Service;

import com.example.foodserver.DTO.MealSelectionDTO;
import com.example.foodserver.DTO.MealSelectionRequestDTO;
import com.example.foodserver.Entity.MealSelectionEntity;

import java.util.List;

public interface MealSelectionService {
    MealSelectionDTO getById(Long mealSelectionId);
    List<MealSelectionEntity> convertToMealSelectionEntities(List<MealSelectionRequestDTO> mealSelectionDTOS);
    List<MealSelectionDTO> convertToMealSelectionDTOS(List<MealSelectionEntity> mealSelectionEntities);
}