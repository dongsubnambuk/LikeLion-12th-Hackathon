package com.demo.nimn.dao.diet;

import com.demo.nimn.dto.diet.FoodNutritionDTO;

import java.util.List;
import java.util.Optional;

public interface FoodMenuDAO {
    FoodNutritionDTO create(FoodNutritionDTO foodNutritionDTO);
    Optional<FoodNutritionDTO> getById(Long id);
    List<FoodNutritionDTO> getAll();
    FoodNutritionDTO update(Long id, FoodNutritionDTO foodNutritionDTO);
    void delete(Long id);
}
