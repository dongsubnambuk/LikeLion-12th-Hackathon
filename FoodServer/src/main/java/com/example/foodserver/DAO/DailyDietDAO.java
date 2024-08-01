package com.example.foodserver.DAO;

import com.example.foodserver.DTO.DailyDietDTO;

import java.util.List;
import java.util.Optional;


public interface DailyDietDAO {
    DailyDietDTO create(DailyDietDTO dailyDietDTO);
    Optional<DailyDietDTO> getById(Long id);
    List<DailyDietDTO> getAll();
    void delete(Long id);
}