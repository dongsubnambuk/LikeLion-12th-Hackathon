package com.demo.nimn.dao.diet;

import com.demo.nimn.entity.diet.FoodSelection;
import com.demo.nimn.repository.diet.FoodSelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MealSelectionDAOImpl implements MealSelectionDAO {

    private final FoodSelectionRepository foodSelectionRepository;

    @Autowired
    public MealSelectionDAOImpl(FoodSelectionRepository foodSelectionRepository) {
        this.foodSelectionRepository = foodSelectionRepository;
    }

    @Override
    public FoodSelection getById(Long mealSelectionId) {
        return foodSelectionRepository.getReferenceById(mealSelectionId);
    }
}