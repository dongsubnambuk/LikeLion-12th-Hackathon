package com.demo.nimn.dao.food;

import com.example.foodserver.Entity.DailyDietEntity;
import com.example.foodserver.Repository.DailyDietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DailyDietDAOImpl implements DailyDietDAO {

    private final DailyDietRepository dailyDietRepository;

    @Autowired
    public DailyDietDAOImpl(DailyDietRepository dailyDietRepository) {
        this.dailyDietRepository = dailyDietRepository;
    }

    @Override
    public List<DailyDietEntity> getByUserEmailAndDate(String userEmail, LocalDate date) {
        return dailyDietRepository.findByUserEmailAndDate(userEmail, date);
    }

    @Override
    public List<DailyDietEntity> getByDate(LocalDate date){
        return dailyDietRepository.findByDate(date);
    }
}