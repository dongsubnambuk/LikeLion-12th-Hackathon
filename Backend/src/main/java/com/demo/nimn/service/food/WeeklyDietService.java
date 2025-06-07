package com.demo.nimn.service.food;

import com.example.foodserver.DTO.Response.WeeklyDietDTO;
import com.example.foodserver.DTO.Request.WeeklyDietRequestDTO;

public interface WeeklyDietService {
    WeeklyDietDTO createWeeklyDiet(WeeklyDietRequestDTO weeklyDietDTO);
    WeeklyDietDTO getWeeklyDietByUserEmail(String userEmail);
}