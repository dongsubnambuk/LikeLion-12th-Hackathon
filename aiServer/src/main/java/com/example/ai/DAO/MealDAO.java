package com.example.ai.DAO;

import com.example.ai.Entity.FoodMenu;

import java.util.List;

public interface MealDAO {
    public Boolean createMeal(FoodMenu foodMenu);
    public List<FoodMenu> findAll();
}
