package com.example.foodserver.Repository;

import com.example.foodserver.Entity.WeeklyDietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeeklyDietRepository extends JpaRepository<WeeklyDietEntity, Long> {
    // 특정 사용자에 대한 WeeklyDiet 목록을 조회하는 메서드
    List<WeeklyDietEntity> findByUserId(Long userId);
}
