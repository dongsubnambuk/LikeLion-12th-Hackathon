package com.example.ai.Repository;

import com.example.ai.Entity.WeeklyMealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface WeeklyMealPlanRepository extends JpaRepository<WeeklyMealPlan, Long> {
    @Query("SELECT wmp FROM WeeklyMealPlan wmp WHERE :currentDate BETWEEN wmp.startDate AND wmp.endDate")
    WeeklyMealPlan findCurrentWeeklyMealPlan(@Param("currentDate") LocalDate currentDate);

    @Query("SELECT COUNT(wmp) > 0 FROM WeeklyMealPlan wmp WHERE :currentDate BETWEEN wmp.startDate AND wmp.endDate")
    Boolean existsByCurrentWeeklyMealPlan(@Param("currentDate") LocalDate currentDate);
}