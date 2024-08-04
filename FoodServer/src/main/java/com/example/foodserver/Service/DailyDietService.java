package com.example.foodserver.Service;

import com.example.foodserver.DTO.DailyDietDTO;
import com.example.foodserver.DTO.DailyDietRequestDTO;
import com.example.foodserver.Entity.DailyDietEntity;

import java.time.LocalDate;
import java.util.List;

public interface DailyDietService {
    List<DailyDietDTO> getByUserEmailAndDate(String userEmail, LocalDate date);
    List<DailyDietEntity> convertToDailyDietEntities(List<DailyDietRequestDTO> dailyDietDTOS);
    List<DailyDietDTO> convertToDailyDietDTOS(List<DailyDietEntity> dailyDietEntities);
}