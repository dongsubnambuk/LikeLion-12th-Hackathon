package com.demo.nimn.service.food;

import com.example.foodserver.DAO.FoodMenuDAO;
import com.example.foodserver.DTO.FoodMenuDTO;
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
    public FoodMenuDTO createFoodMenu(FoodMenuDTO foodMenuDTO) {
        return foodMenuDAO.create(foodMenuDTO);
    }

    @Override
    public Optional<FoodMenuDTO> getFoodMenuById(Long id) {
        return foodMenuDAO.getById(id);
    }

    @Override
    public List<FoodMenuDTO> getAllFoodMenus() {
        return foodMenuDAO.getAll();
    }

    @Override
    public FoodMenuDTO updateFoodMenu(Long id, FoodMenuDTO foodMenuDTO) {
        return foodMenuDAO.update(id, foodMenuDTO);
    }

    @Override
    public void deleteFoodMenu(Long id) {
        foodMenuDAO.delete(id);
    }
}
