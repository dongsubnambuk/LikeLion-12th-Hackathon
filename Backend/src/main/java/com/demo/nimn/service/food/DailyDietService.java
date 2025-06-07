package com.demo.nimn.service.food;

import com.example.foodserver.DTO.Response.DailyDietDTO;
import com.example.foodserver.DTO.Request.DailyDietRequestDTO;
import com.example.foodserver.Entity.DailyDietEntity;

import java.time.LocalDate;
import java.util.List;

public interface DailyDietService {
    List<DailyDietDTO> getByUserEmailAndDate(String userEmail, LocalDate date);
    List<DailyDietDTO> getByDate(LocalDate date);
    List<DailyDietEntity> convertToDailyDietEntities(List<DailyDietRequestDTO> dailyDietDTOS);
    List<DailyDietDTO> convertToDailyDietDTOS(List<DailyDietEntity> dailyDietEntities);
}