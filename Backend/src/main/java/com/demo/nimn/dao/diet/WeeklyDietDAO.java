package com.demo.nimn.dao.diet;

import com.demo.nimn.entity.diet.WeeklyDiet;

import java.time.LocalDate;

public interface WeeklyDietDAO {
    void create(WeeklyDiet weeklyDiet);
    WeeklyDiet getByUserEmail(String userEmail);
    public Boolean existsByCurrentWeeklyMealPlan(LocalDate date, String userEmail);
}