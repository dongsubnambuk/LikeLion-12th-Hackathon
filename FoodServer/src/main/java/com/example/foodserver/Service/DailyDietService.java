package com.example.foodserver.Service;

import com.example.foodserver.DTO.DailyDietDTO;

import java.util.List;
import java.util.Optional;

public interface DailyDietService {
    DailyDietDTO create(DailyDietDTO dailyDietDTO);
    Optional<DailyDietDTO> getDailyDietById(Long dailyDietId);
    List<DailyDietDTO> getAll();
    DailyDietDTO update(Long dailyDietId, DailyDietDTO dailyDietDTO);
    void delete(Long dailyDietId);
    List<DailyDietDTO> getByDayOfWeek(String dayOfWeek);
    List<DailyDietDTO> getByUserId(Long userId);
}
