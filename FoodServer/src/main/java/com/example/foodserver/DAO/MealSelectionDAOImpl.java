package com.example.foodserver.DAO;

import com.example.foodserver.Entity.MealSelectionEntity;
import com.example.foodserver.Repository.MealSelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MealSelectionDAOImpl implements MealSelectionDAO {

    private final MealSelectionRepository mealSelectionRepository;

    @Autowired
    public MealSelectionDAOImpl(MealSelectionRepository mealSelectionRepository) {
        this.mealSelectionRepository = mealSelectionRepository;
    }

    @Override
    public MealSelectionEntity getById(Long mealSelectionId) {
        return mealSelectionRepository.getReferenceById(mealSelectionId);
    }
}