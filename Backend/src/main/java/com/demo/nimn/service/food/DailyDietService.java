package com.demo.nimn.service.food;

import com.demo.nimn.dto.food.Response.DailyDietDTO;
import com.demo.nimn.dto.food.Request.DailyDietRequestDTO;
import com.demo.nimn.entity.food.DailyDiet;

import java.time.LocalDate;
import java.util.List;

public interface DailyDietService {
    List<DailyDietDTO> getByUserEmailAndDate(String userEmail, LocalDate date);
    List<DailyDietDTO> getByDate(LocalDate date);
    List<DailyDiet> convertToDailyDietEntities(List<DailyDietRequestDTO> dailyDietDTOS);
    List<DailyDietDTO> convertToDailyDietDTOS(List<DailyDiet> dailyDietEntities);
}