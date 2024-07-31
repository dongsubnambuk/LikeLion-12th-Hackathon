package com.example.ai.DAO;

import com.example.ai.Entity.FoodMenu;
import com.example.ai.Repository.DietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DietDaoImpl implements DietDao{
    private final DietRepository dietRepository;

    @Autowired
    public DietDaoImpl(DietRepository dietRepository) {
        this.dietRepository = dietRepository;
    }

    @Override
    public Boolean createDiet(FoodMenu foodMenu) {
        dietRepository.save(foodMenu);

        return dietRepository.existsById(foodMenu.getId());
    }
}
