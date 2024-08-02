package com.example.foodserver.DAO;

import com.example.foodserver.Entity.MealSelectionEntity;
import com.example.foodserver.Repository.MealSelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MealSelectionDAOImpl implements MealSelectionDAO {

    private final MealSelectionRepository mealSelectionRepository;

    @Autowired
    public MealSelectionDAOImpl(MealSelectionRepository mealSelectionRepository) {
        this.mealSelectionRepository = mealSelectionRepository;
    }

    @Override
    public MealSelectionEntity create(MealSelectionEntity mealSelectionEntity) {
        return mealSelectionRepository.save(mealSelectionEntity);
    }

    @Override
    public Optional<MealSelectionEntity> getById(Long mealSelectionId) {
        return mealSelectionRepository.findById(mealSelectionId);
    }

    @Override
    public List<MealSelectionEntity> getAll() {
        return mealSelectionRepository.findAll();
    }

    @Override
    public List<MealSelectionEntity> getByUserId(Long userId) {
        return mealSelectionRepository.findByUserId(userId);
    }

    @Override
    public List<MealSelectionEntity> getByDailyDietId(Long dailyDietId) {
        return mealSelectionRepository.findByDailyDietId(dailyDietId);
    }

    @Override
    public List<MealSelectionEntity> getByMealTime(String mealTime) {
        return mealSelectionRepository.findByMealTime(mealTime);
    }

    @Override
    public MealSelectionEntity update(Long mealSelectionId, MealSelectionEntity mealSelectionEntity) {
        if (mealSelectionRepository.existsById(mealSelectionId)) {
            mealSelectionEntity.setMealSelectionId(mealSelectionId);
            return mealSelectionRepository.save(mealSelectionEntity);
        } else {
            throw new RuntimeException(mealSelectionId + "선택한 식단을 업데이트하지 못했습니다.");
        }
    }

    @Override
    public void delete(Long mealSelectionId) {
        if (mealSelectionRepository.existsById(mealSelectionId)) {
            mealSelectionRepository.deleteById(mealSelectionId);
        } else {
            throw new RuntimeException(mealSelectionId + "선택했던 식단을 삭제하지 못했습니다.");
        }
    }
}
