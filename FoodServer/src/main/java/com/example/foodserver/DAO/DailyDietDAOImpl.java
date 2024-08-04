package com.example.foodserver.DAO;

import com.example.foodserver.Entity.DailyDietEntity;
import com.example.foodserver.Repository.DailyDietRepository;
import jakarta.persistence.TypedQuery;
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
    public Optional<DailyDietEntity> getByDailyId(Long dailyDietId) {
        return dailyDietRepository.findById(dailyDietId);
    }

    @Override
    public List<DailyDietEntity> getAll() {
        return dailyDietRepository.findAll();
    }

    @Override
    public DailyDietEntity update(Long dailyDietId, DailyDietEntity dailyDietEntity) {
        if (dailyDietRepository.existsById(dailyDietId)) {
            dailyDietEntity.setDailyDietId(dailyDietId);
            return dailyDietRepository.save(dailyDietEntity);
        } else {
            throw new RuntimeException(dailyDietId + "에 해당하는 식단을 업데이트할 수 없습니다.");
        }
    }

    @Override
    public void delete(Long dailyDietId) {
        if (dailyDietRepository.existsById(dailyDietId)) {
            dailyDietRepository.deleteById(dailyDietId);
        } else {
            throw new RuntimeException(dailyDietId + "에 해당하는 식단을 삭제할 수 없습니다.");
        }
    }

    @Override
    public List<DailyDietEntity> getByDayOfWeek(String dayOfWeek) {
        return dailyDietRepository.findByDayOfWeek(dayOfWeek);
    }

    @Override
    public List<DailyDietEntity> getByUserId(Long userId) {
        return dailyDietRepository.findByUserId(userId);
    }
}
