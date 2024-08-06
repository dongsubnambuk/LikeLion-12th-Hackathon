package com.example.foodserver.DAO;

import com.example.foodserver.Entity.MealSelectionEntity;

import java.util.List;

public interface MealSelectionDAO {
    MealSelectionEntity getById(Long mealSelectionId);
}