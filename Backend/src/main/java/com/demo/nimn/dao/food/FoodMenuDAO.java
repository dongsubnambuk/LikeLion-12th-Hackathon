package com.demo.nimn.dao.food;

import com.demo.nimn.dto.food.FoodMenuDTO;

import java.util.List;
import java.util.Optional;

public interface FoodMenuDAO {
    FoodMenuDTO create(FoodMenuDTO foodMenuDTO);
    Optional<FoodMenuDTO> getById(Long id);
    List<FoodMenuDTO> getAll();
    FoodMenuDTO update(Long id, FoodMenuDTO foodMenuDTO);
    void delete(Long id);
}
