package com.demo.nimn.dao.meal;

import com.example.ai.Entity.Meal.WeeklyMealPlan;

import java.time.LocalDate;

public interface WeeklyMealPlanDAO {
    public void createWeeklyMealPlan(WeeklyMealPlan weeklyMealPlan);
    public WeeklyMealPlan findCurrentWeeklyMealPlan(LocalDate currentDate);
    public Boolean existsByCurrentWeeklyMealPlan(LocalDate currentDate);
}
