package com.demo.nimn.dao.food;

import com.example.foodserver.Entity.DailyDietEntity;

import java.time.LocalDate;
import java.util.List;

public interface DailyDietDAO {
    List<DailyDietEntity> getByUserEmailAndDate(String userEmail, LocalDate date);
    List<DailyDietEntity> getByDate(LocalDate date);
}