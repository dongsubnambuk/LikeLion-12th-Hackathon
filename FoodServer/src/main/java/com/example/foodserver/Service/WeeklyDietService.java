package com.example.foodserver.Service;

import com.example.foodserver.DTO.WeeklyDietDTO;

import java.util.List;
import java.util.Optional;

public interface WeeklyDietService {
    WeeklyDietDTO createWeeklyDiet(WeeklyDietDTO weeklyDietDTO);
    Optional<WeeklyDietDTO> getWeeklyDietById(Long id);
    List<WeeklyDietDTO> getAllWeeklyDiets();
    void deleteWeeklyDiet(Long id);
}
