package com.demo.nimn.service.food;

import com.demo.nimn.dto.food.FoodDTO;
import com.demo.nimn.entity.food.Food;

import java.util.List;

public interface FoodService {
    FoodDTO createFood(Long price);
    FoodDTO readFoodDTOByFoodId(Long foodId);
    List<FoodDTO> readAll();
    FoodDTO convertToFoodDTO(Food food);
}
