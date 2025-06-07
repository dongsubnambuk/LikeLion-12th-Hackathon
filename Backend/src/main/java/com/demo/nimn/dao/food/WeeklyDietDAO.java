package com.demo.nimn.dao.food;

import com.demo.nimn.entity.food.WeeklyDiet;

import java.time.LocalDate;

public interface WeeklyDietDAO {
    void create(WeeklyDiet weeklyDiet);
    WeeklyDiet getByUserEmail(String userEmail);
    public Boolean existsByCurrentWeeklyMealPlan(LocalDate date, String userEmail);
}