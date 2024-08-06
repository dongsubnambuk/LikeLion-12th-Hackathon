package com.example.ai.DAO.Meal;

import com.example.ai.Entity.Meal.FoodMenu;
import com.example.ai.Repository.Meal.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MealDAOImpl implements MealDAO {
    private final MealRepository mealRepository;

    @Autowired
    public MealDAOImpl(MealRepository dietRepository) {
        this.mealRepository = dietRepository;
    }

    @Override
    public Boolean createMeal(FoodMenu foodMenu) {
        mealRepository.save(foodMenu);

        return mealRepository.existsById(foodMenu.getFoodMenuId());
    }

    @Override
    public List<FoodMenu> findAll(){
        return mealRepository.findAll();
    }

    @Override
    public FoodMenu findById(Long foodMenuId){
        return mealRepository.getReferenceById(foodMenuId);
    }
}
