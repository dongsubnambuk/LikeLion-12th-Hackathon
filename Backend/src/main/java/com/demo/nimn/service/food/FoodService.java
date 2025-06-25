package com.demo.nimn.service.food;

import com.demo.nimn.dto.food.FoodDTO;
import com.demo.nimn.entity.food.Food;

import java.util.List;

public interface FoodService {
    FoodDTO createFood(String price);
    Food readFoodByFoodId(Long foodMenuId);
    FoodDTO readFoodDTOByFoodId(Long foodMenuId);
    List<FoodDTO> readAll();
}
