package com.demo.nimn.service.meal;

import com.example.ai.DTO.Meal.FoodMenuDTO;
import com.example.ai.DTO.Meal.WeeklyMealPlanDTO;
import com.example.ai.Entity.Meal.FoodMenu;

import java.util.List;

public interface MealService {
    public FoodMenuDTO createMeal(String price);
    public WeeklyMealPlanDTO createWeeklyMealPlan();
    public WeeklyMealPlanDTO readWeeklyMealPlan();
    public FoodMenu readFoodMenuByFoodMenuId(Long foodMenuId);
    public FoodMenuDTO readFoodMenuDTOByFoodMenuId(Long foodMenuId);
    public List<FoodMenuDTO> readAll();
}
