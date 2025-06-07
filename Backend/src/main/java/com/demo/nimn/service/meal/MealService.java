package com.demo.nimn.service.meal;

import com.demo.nimn.dto.meal.FoodMenuDTO;
import com.demo.nimn.dto.meal.WeeklyMealPlanDTO;
import com.demo.nimn.entity.meal.FoodMenu;

import java.util.List;

public interface MealService {
    public FoodMenuDTO createMeal(String price);
    public WeeklyMealPlanDTO createWeeklyMealPlan();
    public WeeklyMealPlanDTO readWeeklyMealPlan();
    public FoodMenu readFoodMenuByFoodMenuId(Long foodMenuId);
    public FoodMenuDTO readFoodMenuDTOByFoodMenuId(Long foodMenuId);
    public List<FoodMenuDTO> readAll();
}
