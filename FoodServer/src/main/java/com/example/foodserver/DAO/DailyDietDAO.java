package com.example.foodserver.DAO;

import com.example.foodserver.Entity.DailyDietEntity;

import java.util.List;
import java.util.Optional;

public interface DailyDietDAO {
    DailyDietEntity create(DailyDietEntity dailyDietEntity);
    Optional<DailyDietEntity> getByDailyId(Long dailyId);
    List<DailyDietEntity> getAll();
    DailyDietEntity update(Long dailyId, DailyDietEntity dailyDietEntity);
    void delete(Long dailyId);
    List<DailyDietEntity> getByDayOfWeek(String dayOfWeek); // 추가된 메서드
}

