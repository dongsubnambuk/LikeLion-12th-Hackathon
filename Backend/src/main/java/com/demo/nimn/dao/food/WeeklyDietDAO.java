package com.demo.nimn.dao.food;

import com.example.foodserver.Entity.WeeklyDietEntity;

import java.time.LocalDate;

public interface WeeklyDietDAO {
    void create(WeeklyDietEntity weeklyDietEntity);
    WeeklyDietEntity getByUserEmail(String userEmail);
    public Boolean existsByCurrentWeeklyMealPlan(LocalDate date, String userEmail);
}