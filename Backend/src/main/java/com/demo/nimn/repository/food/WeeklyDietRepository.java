package com.demo.nimn.repository.food;

import com.example.foodserver.Entity.WeeklyDietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface WeeklyDietRepository extends JpaRepository<WeeklyDietEntity, Long> {
  WeeklyDietEntity findByUserEmail(String userEmail);
  @Query("SELECT COUNT(wde) > 0 FROM WeeklyDietEntity wde WHERE :currentDate BETWEEN wde.startDate AND wde.endDate AND wde.userEmail = :userEmail")
  Boolean existsByCurrentWeeklyMealPlan(@Param("currentDate") LocalDate currentDate, @Param("userEmail") String userEmail);
}