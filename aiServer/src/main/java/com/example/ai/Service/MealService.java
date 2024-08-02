package com.example.ai.Service;

import com.example.ai.DTO.FoodMenuDTO;
import com.example.ai.DTO.WeeklyMealPlanDTO;

public interface MealService {
    public FoodMenuDTO createMeal(String price);
    public WeeklyMealPlanDTO createWeeklyMealPlan();
    public WeeklyMealPlanDTO readWeeklyMealPlan();
}
