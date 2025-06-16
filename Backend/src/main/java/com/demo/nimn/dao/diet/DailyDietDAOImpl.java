package com.demo.nimn.dao.diet;

import com.demo.nimn.entity.diet.DailyDiet;
import com.demo.nimn.repository.diet.DailyDietRepository;
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
    public List<DailyDiet> getByUserEmailAndDate(String userEmail, LocalDate date) {
        return dailyDietRepository.findByUserEmailAndDate(userEmail, date);
    }

    @Override
    public List<DailyDiet> getByDate(LocalDate date){
        return dailyDietRepository.findByDate(date);
    }
}