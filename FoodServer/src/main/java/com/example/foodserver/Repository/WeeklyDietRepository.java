package com.example.foodserver.Repository;

import com.example.foodserver.Entity.WeeklyDietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyDietRepository extends JpaRepository<WeeklyDietEntity, Long> {
  WeeklyDietEntity findByUserEmail(String userEmail);
}