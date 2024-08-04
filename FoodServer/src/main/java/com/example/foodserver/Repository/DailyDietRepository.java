package com.example.foodserver.Repository;

import com.example.foodserver.Entity.DailyDietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyDietRepository extends JpaRepository<DailyDietEntity, Long> {
    List<DailyDietEntity> findByUserEmailAndDate(String userEmail, LocalDate date);

}