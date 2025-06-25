package com.demo.nimn.dao.meal;

import com.demo.nimn.entity.food.WeeklyFoodPlan;

import java.time.LocalDate;

public interface WeeklyMealPlanDAO {
    public void createWeeklyMealPlan(WeeklyFoodPlan weeklyFoodPlan);
    public WeeklyFoodPlan findCurrentWeeklyMealPlan(LocalDate currentDate);
    public Boolean existsByCurrentWeeklyMealPlan(LocalDate currentDate);
}
