package com.example.foodserver.DAO;

import com.example.foodserver.Entity.DailyDietEntity;
import com.example.foodserver.Repository.DailyDietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DailyDietDAOImpl implements DailyDietDAO {

    private final DailyDietRepository dailyDietRepository;

    @Autowired
    public DailyDietDAOImpl(DailyDietRepository dailyDietRepository) {
        this.dailyDietRepository = dailyDietRepository;
    }

    @Override
    public DailyDietEntity create(DailyDietEntity dailyDietEntity) {
        return dailyDietRepository.save(dailyDietEntity);
    }

    @Override
    public Optional<DailyDietEntity> getByDailyId(Long dailyId) {
        return dailyDietRepository.findById(dailyId);
    }

    @Override
    public List<DailyDietEntity> getAll() {
        return dailyDietRepository.findAll();
    }

    @Override
    public DailyDietEntity update(Long dailyId, DailyDietEntity dailyDietEntity) {
        if (dailyDietRepository.existsById(dailyId)) {
            dailyDietEntity.setDailyId(dailyId);
            return dailyDietRepository.save(dailyDietEntity);
        } else {
            throw new RuntimeException(dailyId + "에 대한 일일 식단 업데이트를 실패했습니다.");
        }
    }

    @Override
    public void delete(Long dailyId) {
        if (dailyDietRepository.existsById(dailyId)) {
            dailyDietRepository.deleteById(dailyId);
        } else {
            throw new RuntimeException(dailyId + "특정 요일의 식단을 찾지 못했습니다.");
        }
    }

    @Override
    public List<DailyDietEntity> getByDayOfWeek(String dayOfWeek) {
        return dailyDietRepository.findByDayOfWeek(dayOfWeek);
    }
}
