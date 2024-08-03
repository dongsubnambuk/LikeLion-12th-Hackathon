package com.example.ai.Service.Meal;

import com.example.ai.DTO.Meal.FoodMenuDTO;
import com.example.ai.DTO.Meal.WeeklyMealPlanDTO;
import com.example.ai.Entity.Meal.FoodMenu;

public interface MealService {
    public FoodMenuDTO createMeal(String price);
    public WeeklyMealPlanDTO createWeeklyMealPlan();
    public WeeklyMealPlanDTO readWeeklyMealPlan();
    public FoodMenu readFoodMenuByFoodMenuId(Long foodMenuId);
}
