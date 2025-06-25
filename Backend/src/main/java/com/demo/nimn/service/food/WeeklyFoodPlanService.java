package com.demo.nimn.service.food;

import com.demo.nimn.dto.food.WeeklyFoodPlanDTO;

public interface WeeklyFoodPlanService {
    WeeklyFoodPlanDTO createWeeklyPlan();
    WeeklyFoodPlanDTO readWeeklyFoodPlan();
}
