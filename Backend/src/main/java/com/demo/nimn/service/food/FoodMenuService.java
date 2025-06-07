package com.demo.nimn.service.food;

import com.demo.nimn.dto.food.FoodMenuDTO;

import java.util.List;
import java.util.Optional;

public interface FoodMenuService {
    FoodMenuDTO createFoodMenu(FoodMenuDTO foodMenuDTO);
    Optional<FoodMenuDTO> getFoodMenuById(Long id);
    List<FoodMenuDTO> getAllFoodMenus();
    FoodMenuDTO updateFoodMenu(Long id, FoodMenuDTO foodMenuDTO);
    void deleteFoodMenu(Long id);
}
