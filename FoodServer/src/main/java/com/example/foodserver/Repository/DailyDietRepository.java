package com.example.foodserver.Repository;

import com.example.foodserver.Entity.DailyDietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;


@Repository
public interface DailyDietRepository extends JpaRepository<DailyDietEntity, Long> {
    List<DailyDietEntity> findByDayOfWeek(DayOfWeek dayOfWeek);
}
