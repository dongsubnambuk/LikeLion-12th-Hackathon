package com.demo.nimn.repository.diet;

import com.demo.nimn.entity.diet.DailyDiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyDietRepository extends JpaRepository<DailyDiet, Long> {
    List<DailyDiet> findByUserEmailAndDate(String userEmail, LocalDate date);
    List<DailyDiet> findByDate(LocalDate date);
}