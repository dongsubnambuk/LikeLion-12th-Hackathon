package com.example.foodserver.DAO;

import com.example.foodserver.Entity.WeeklyDietEntity;
import java.util.List;
import java.util.Optional;

public interface WeeklyDietDAO {
    WeeklyDietEntity create(WeeklyDietEntity weeklyDietEntity);
    Optional<WeeklyDietEntity> getByWeeklyId(Long weeklyId);
    List<WeeklyDietEntity> getAll();
    void delete(Long weeklyId);
}
