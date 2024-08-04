package com.example.foodserver.Service;

import com.example.foodserver.DTO.WeeklyDietDTO;

public interface WeeklyDietService {
    WeeklyDietDTO createWeeklyDiet(WeeklyDietDTO weeklyDietDTO);
    WeeklyDietDTO getWeeklyDietByUserEmail(String userEmail);
}