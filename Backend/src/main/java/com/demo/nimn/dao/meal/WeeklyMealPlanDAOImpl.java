package com.demo.nimn.dao.meal;

import com.demo.nimn.entity.food.WeeklyFoodPlan;
import com.demo.nimn.repository.meal.WeeklyFoodPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class WeeklyMealPlanDAOImpl implements WeeklyMealPlanDAO {
    private final WeeklyFoodPlanRepository weeklyFoodPlanRepository;

    @Autowired
    public WeeklyMealPlanDAOImpl(WeeklyFoodPlanRepository weeklyFoodPlanRepository) {
        this.weeklyFoodPlanRepository = weeklyFoodPlanRepository;
    }

    @Override
    public void createWeeklyMealPlan(WeeklyFoodPlan weeklyFoodPlan) {
        weeklyFoodPlanRepository.save(weeklyFoodPlan);
    }

    @Override
    public WeeklyFoodPlan findCurrentWeeklyMealPlan(LocalDate currentDate) {
        return weeklyFoodPlanRepository.findCurrentWeeklyFoodPlan(currentDate);
    }

    @Override
    public Boolean existsByCurrentWeeklyMealPlan(LocalDate currentDate) {
        return weeklyFoodPlanRepository.existsByCurrentWeeklyFoodPlan(currentDate);
    }
}
