package com.demo.nimn.dao.food;

import com.demo.nimn.entity.food.WeeklyDiet;
import com.demo.nimn.repository.food.WeeklyDietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class WeeklyDietDAOImpl implements WeeklyDietDAO {

    private final WeeklyDietRepository weeklyDietRepository;

    @Autowired
    public WeeklyDietDAOImpl(WeeklyDietRepository weeklyDietRepository) {
        this.weeklyDietRepository = weeklyDietRepository;
    }

    @Override
    public void create(WeeklyDiet weeklyDiet) {
        weeklyDietRepository.save(weeklyDiet);
    }

    @Override
    public WeeklyDiet getByUserEmail(String userEmail) {
        return weeklyDietRepository.findByUserEmail(userEmail);
    }

    @Override
    public Boolean existsByCurrentWeeklyMealPlan(LocalDate date, String userEmail){
        return weeklyDietRepository.existsByCurrentWeeklyMealPlan(date, userEmail);
    }
}
