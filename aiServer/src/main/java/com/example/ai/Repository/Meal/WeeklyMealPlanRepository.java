package com.example.ai.Repository.Meal;

import com.example.ai.Entity.Meal.WeeklyMealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface WeeklyMealPlanRepository extends JpaRepository<WeeklyMealPlan, Long> {
    @Query("SELECT wmp FROM WeeklyMealPlan wmp WHERE :currentDate BETWEEN wmp.startDate AND wmp.endDate")
    WeeklyMealPlan findCurrentWeeklyMealPlan(@Param("currentDate") LocalDate currentDate);

    @Query("SELECT COUNT(wmp) > 0 FROM WeeklyMealPlan wmp WHERE :currentDate BETWEEN wmp.startDate AND wmp.endDate")
    Boolean existsByCurrentWeeklyMealPlan(@Param("currentDate") LocalDate currentDate);
}