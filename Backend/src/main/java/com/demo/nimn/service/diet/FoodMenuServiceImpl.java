package com.demo.nimn.service.diet;

import com.demo.nimn.dao.diet.FoodMenuDAO;
import com.demo.nimn.dto.diet.FoodNutritionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodMenuServiceImpl implements FoodMenuService {

    private final FoodMenuDAO foodMenuDAO;

    @Autowired
    public FoodMenuServiceImpl(FoodMenuDAO foodMenuDAO) {
        this.foodMenuDAO = foodMenuDAO;
    }

    @Override
    public FoodNutritionDTO createFoodMenu(FoodNutritionDTO foodNutritionDTO) {
        return foodMenuDAO.create(foodNutritionDTO);
    }

    @Override
    public Optional<FoodNutritionDTO> getFoodMenuById(Long id) {
        return foodMenuDAO.getById(id);
    }

    @Override
    public List<FoodNutritionDTO> getAllFoodMenus() {
        return foodMenuDAO.getAll();
    }

    @Override
    public FoodNutritionDTO updateFoodMenu(Long id, FoodNutritionDTO foodNutritionDTO) {
        return foodMenuDAO.update(id, foodNutritionDTO);
    }

    @Override
    public void deleteFoodMenu(Long id) {
        foodMenuDAO.delete(id);
    }
}
