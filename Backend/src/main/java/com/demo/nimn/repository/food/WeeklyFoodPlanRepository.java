package com.demo.nimn.repository.food;

import com.demo.nimn.entity.food.WeeklyFoodPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface WeeklyFoodPlanRepository extends JpaRepository<WeeklyFoodPlan, Long> {
    @Query("SELECT wfp FROM WeeklyFoodPlan wfp WHERE :currentDate BETWEEN wfp.startDate AND wfp.endDate")
    WeeklyFoodPlan findCurrentWeeklyFoodPlan(@Param("currentDate") LocalDate currentDate);

    @Query("SELECT COUNT(wfp) > 0 FROM WeeklyFoodPlan wfp WHERE :currentDate BETWEEN wfp.startDate AND wfp.endDate")
    Boolean existsByCurrentWeeklyFoodPlan(@Param("currentDate") LocalDate currentDate);
}