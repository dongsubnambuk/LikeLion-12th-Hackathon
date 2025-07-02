package com.demo.nimn.service.diet;

import com.demo.nimn.dto.diet.WeeklyDietRequestDTO;
import com.demo.nimn.dto.diet.DailyDietDTO;
import com.demo.nimn.dto.diet.DailyDietRequestDTO;
import com.demo.nimn.dto.diet.WeeklyDietDTO;
import com.demo.nimn.entity.diet.DailyDiet;

import java.time.LocalDate;
import java.util.List;

public interface DietService {
    // DailyDiet
    List<DailyDietDTO> getByUserEmailAndDate(String userEmail, LocalDate date);
    List<DailyDietDTO> getByDate(LocalDate date);
    List<DailyDiet> convertToDailyDietEntities(List<DailyDietRequestDTO> dailyDietDTOS);
    List<DailyDietDTO> convertToDailyDietDTOS(List<DailyDiet> dailyDietEntities);

    // WeeklyDiet
    WeeklyDietDTO createWeeklyDiet(WeeklyDietRequestDTO weeklyDietDTO);
    WeeklyDietDTO getWeeklyDietByUserEmail(String userEmail);
    WeeklyDietDTO getWeeklyDietById(Long weeklyDietId);
}