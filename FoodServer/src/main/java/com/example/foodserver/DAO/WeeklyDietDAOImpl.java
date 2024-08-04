package com.example.foodserver.DAO;

import com.example.foodserver.Entity.WeeklyDietEntity;
import com.example.foodserver.Repository.WeeklyDietRepository;
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
    public void create(WeeklyDietEntity weeklyDietEntity) {
        weeklyDietRepository.save(weeklyDietEntity);
    }

    @Override
    public WeeklyDietEntity getByUserEmail(String userEmail) {
        return weeklyDietRepository.findByUserEmail(userEmail);
    }

    @Override
    public Boolean existsByCurrentWeeklyMealPlan(LocalDate date, String userEmail){
        return weeklyDietRepository.existsByCurrentWeeklyMealPlan(date, userEmail);
    }
}
