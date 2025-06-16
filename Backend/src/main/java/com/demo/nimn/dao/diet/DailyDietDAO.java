package com.demo.nimn.dao.diet;

import com.demo.nimn.entity.diet.DailyDiet;

import java.time.LocalDate;
import java.util.List;

public interface DailyDietDAO {
    List<DailyDiet> getByUserEmailAndDate(String userEmail, LocalDate date);
    List<DailyDiet> getByDate(LocalDate date);
}