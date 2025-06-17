package com.demo.nimn.service.diet;

import com.demo.nimn.dto.diet.Response.FoodSelectionDTO;
import com.demo.nimn.dto.diet.Request.FoodSelectionRequestDTO;
import com.demo.nimn.entity.diet.FoodSelection;

import java.util.List;

public interface FoodSelectionService {
    FoodSelectionDTO getById(Long mealSelectionId);
    List<FoodSelection> convertToMealSelectionEntities(List<FoodSelectionRequestDTO> mealSelectionDTOS);
    List<FoodSelectionDTO> convertToMealSelectionDTOS(List<FoodSelection> foodSelectionEntities);
}