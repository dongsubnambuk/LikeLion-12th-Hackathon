package com.demo.nimn.service.diet;

import com.demo.nimn.dto.diet.FoodNutritionDTO;

import java.util.List;
import java.util.Optional;

public interface FoodMenuService {
    FoodNutritionDTO createFoodMenu(FoodNutritionDTO foodNutritionDTO);
    Optional<FoodNutritionDTO> getFoodMenuById(Long id);
    List<FoodNutritionDTO> getAllFoodMenus();
    FoodNutritionDTO updateFoodMenu(Long id, FoodNutritionDTO foodNutritionDTO);
    void deleteFoodMenu(Long id);
}
