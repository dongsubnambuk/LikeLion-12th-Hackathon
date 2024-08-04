package com.example.foodserver.DAO;

import com.example.foodserver.Entity.DailyDietEntity;

import java.util.List;
import java.util.Optional;

public interface DailyDietDAO {
    DailyDietEntity create(DailyDietEntity dailyDietEntity);
    Optional<DailyDietEntity> getByDailyId(Long dailyDietId);
    List<DailyDietEntity> getAll();
    DailyDietEntity update(Long dailyDietId, DailyDietEntity dailyDietEntity);
    void delete(Long dailyDietId);
    List<DailyDietEntity> getByDayOfWeek(String dayOfWeek);
    List<DailyDietEntity> getByUserId(Long userId); // 메서드 선언
}
