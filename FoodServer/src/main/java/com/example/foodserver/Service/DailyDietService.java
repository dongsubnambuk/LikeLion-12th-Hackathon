package com.example.foodserver.Service;

import com.example.foodserver.DTO.DailyDietDTO;

import java.util.List;
import java.util.Optional;

public interface DailyDietService {
    DailyDietDTO createDailyDiet(DailyDietDTO dailyDietDTO);
    Optional<DailyDietDTO> getDailyDietById(Long id);
    List<DailyDietDTO> getAllDailyDiets();
    void deleteDailyDiet(Long id);
}
