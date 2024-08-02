package com.example.ai.DAO;

import com.example.ai.Entity.WeeklyMealPlan;
import com.example.ai.Repository.WeeklyMealPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
