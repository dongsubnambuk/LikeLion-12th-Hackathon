package com.example.ai.DAO;

import com.example.ai.Entity.FoodMenu;
import com.example.ai.Repository.DietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MealDAOImpl implements MealDAO {
    private final DietRepository dietRepository;

    @Autowired
    public MealDAOImpl(DietRepository dietRepository) {
        this.dietRepository = dietRepository;
    }

    @Override
    public Boolean createMeal(FoodMenu foodMenu) {
        dietRepository.save(foodMenu);

        return dietRepository.existsById(foodMenu.getFoodMenuId());
    }

    @Override
    public List<FoodMenu> findAll(){
        return dietRepository.findAll();
    }
}
