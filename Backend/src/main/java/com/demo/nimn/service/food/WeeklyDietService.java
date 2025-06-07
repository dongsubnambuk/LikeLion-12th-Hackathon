package com.demo.nimn.service.food;

import com.demo.nimn.dto.food.Response.WeeklyDietDTO;
import com.demo.nimn.dto.food.Request.WeeklyDietRequestDTO;

public interface WeeklyDietService {
    WeeklyDietDTO createWeeklyDiet(WeeklyDietRequestDTO weeklyDietDTO);
    WeeklyDietDTO getWeeklyDietByUserEmail(String userEmail);
}