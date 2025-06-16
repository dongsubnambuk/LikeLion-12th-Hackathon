package com.demo.nimn.dao.meal;

import com.demo.nimn.entity.meal.FoodMenu;
import com.demo.nimn.repository.meal.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FoodDAOImpl implements FoodDAO {
    private final MealRepository mealRepository;

    @Autowired
    public FoodDAOImpl(MealRepository dietRepository) {
        this.mealRepository = dietRepository;
    }

    @Override
    public Boolean createMeal(FoodMenu foodMenu) {
        mealRepository.save(foodMenu);

        return mealRepository.existsById(foodMenu.getId());
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
