package com.example.ai.DAO.Meal;

import com.example.ai.Entity.Meal.WeeklyMealPlan;
import com.example.ai.Repository.Meal.WeeklyMealPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class WeeklyMealPlanDAOImpl implements WeeklyMealPlanDAO {
    private final WeeklyMealPlanRepository weeklyMealPlanRepository;

    @Autowired
    public WeeklyMealPlanDAOImpl(WeeklyMealPlanRepository weeklyMealPlanRepository) {
        this.weeklyMealPlanRepository = weeklyMealPlanRepository;
    }

    @Override
    public void createWeeklyMealPlan(WeeklyMealPlan weeklyMealPlan) {
        weeklyMealPlanRepository.save(weeklyMealPlan);
    }

    @Override
    public WeeklyMealPlan findCurrentWeeklyMealPlan(LocalDate currentDate) {
        return weeklyMealPlanRepository.findCurrentWeeklyMealPlan(currentDate);
    }

    @Override
    public Boolean existsByCurrentWeeklyMealPlan(LocalDate currentDate) {
        return weeklyMealPlanRepository.existsByCurrentWeeklyMealPlan(currentDate);
    }
}
