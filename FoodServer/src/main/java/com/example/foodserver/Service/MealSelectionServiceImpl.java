package com.example.foodserver.Service;

import com.example.foodserver.DAO.MealSelectionDAO;
import com.example.foodserver.DTO.MealSelectionDTO;
import com.example.foodserver.Entity.MealSelectionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MealSelectionServiceImpl implements MealSelectionService {

    private final MealSelectionDAO mealSelectionDAO;

    @Autowired
    public MealSelectionServiceImpl(MealSelectionDAO mealSelectionDAO) {
        this.mealSelectionDAO = mealSelectionDAO;
    }

    @Override
    public MealSelectionDTO createMealSelection(MealSelectionDTO mealSelectionDTO) {
        return mealSelectionDAO.create(mealSelectionDTO);
    }

    @Override
    public Optional<MealSelectionDTO> getMealSelectionById(Long id) {
        return mealSelectionDAO.getById(id);
    }

    @Override
    public List<MealSelectionDTO> getMealSelectionsByUserId(Long userId) {
        return mealSelectionDAO.getByUserId(userId);
    }

    @Override
    public List<MealSelectionDTO> getMealSelectionsByDailyDietId(Long dailyDietId) {
        return mealSelectionDAO.getByDailyDietId(dailyDietId);
    }

    @Override
    public List<MealSelectionDTO> getMealSelectionsByMealTime(String mealTime) {
        return mealSelectionDAO.getByMealTime(mealTime);
    }

    @Override
    public MealSelectionDTO updateMealSelection(Long id, MealSelectionDTO mealSelectionDTO) {
        return mealSelectionDAO.update(id, mealSelectionDTO);
    }

    @Override
    public void deleteMealSelection(Long id) {
        mealSelectionDAO.delete(id);
    }
}
