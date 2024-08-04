package com.example.foodserver.DAO;

import com.example.foodserver.Entity.WeeklyDietEntity;

public interface WeeklyDietDAO {
    WeeklyDietEntity create(WeeklyDietEntity weeklyDietEntity);
    WeeklyDietEntity getByUserEmail(String userEmail);
}