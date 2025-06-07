package com.demo.nimn.service.food;

import com.example.foodserver.DTO.Response.MealSelectionDTO;
import com.example.foodserver.DTO.Request.MealSelectionRequestDTO;
import com.example.foodserver.Entity.MealSelectionEntity;

import java.util.List;

public interface MealSelectionService {
    MealSelectionDTO getById(Long mealSelectionId);
    List<MealSelectionEntity> convertToMealSelectionEntities(List<MealSelectionRequestDTO> mealSelectionDTOS);
    List<MealSelectionDTO> convertToMealSelectionDTOS(List<MealSelectionEntity> mealSelectionEntities);
}