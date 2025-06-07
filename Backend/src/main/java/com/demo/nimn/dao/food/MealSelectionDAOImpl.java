package com.demo.nimn.dao.food;

import com.demo.nimn.entity.food.MealSelection;
import com.demo.nimn.repository.food.MealSelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MealSelectionDAOImpl implements MealSelectionDAO {

    private final MealSelectionRepository mealSelectionRepository;

    @Autowired
    public MealSelectionDAOImpl(MealSelectionRepository mealSelectionRepository) {
        this.mealSelectionRepository = mealSelectionRepository;
    }

    @Override
    public MealSelection getById(Long mealSelectionId) {
        return mealSelectionRepository.getReferenceById(mealSelectionId);
    }
}