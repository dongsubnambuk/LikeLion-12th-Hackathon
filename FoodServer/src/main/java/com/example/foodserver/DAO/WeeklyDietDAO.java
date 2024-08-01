package com.example.foodserver.DAO;

import com.example.foodserver.DTO.WeeklyDietDTO;

import java.util.List;
import java.util.Optional;

public interface WeeklyDietDAO {
    WeeklyDietDTO create(WeeklyDietDTO weeklyDietDTO);
    Optional<WeeklyDietDTO> getById(Long id);
    List<WeeklyDietDTO> getAll();
    void delete(Long id);
}
