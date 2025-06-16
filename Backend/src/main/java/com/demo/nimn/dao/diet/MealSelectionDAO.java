package com.demo.nimn.dao.diet;

import com.demo.nimn.entity.diet.FoodSelection;

public interface MealSelectionDAO {
    FoodSelection getById(Long mealSelectionId);
}