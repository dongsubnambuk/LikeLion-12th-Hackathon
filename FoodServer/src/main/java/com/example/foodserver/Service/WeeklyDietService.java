package com.example.foodserver.Service;

import com.example.foodserver.DTO.WeeklyDietDTO;
import com.example.foodserver.DTO.WeeklyDietRequestDTO;

public interface WeeklyDietService {
    WeeklyDietDTO createWeeklyDiet(WeeklyDietRequestDTO weeklyDietDTO);
    WeeklyDietDTO getWeeklyDietByUserEmail(String userEmail);
}