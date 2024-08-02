package com.example.foodserver.DAO;

import com.example.foodserver.Entity.WeeklyDietEntity;
import com.example.foodserver.Repository.WeeklyDietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WeeklyDietDAOImpl implements WeeklyDietDAO {

    private final WeeklyDietRepository weeklyDietRepository;

    @Autowired
    public WeeklyDietDAOImpl(WeeklyDietRepository weeklyDietRepository) {
        this.weeklyDietRepository = weeklyDietRepository;
    }

    @Override
    public WeeklyDietEntity create(WeeklyDietEntity weeklyDietEntity) {
        return weeklyDietRepository.save(weeklyDietEntity);
    }

    @Override
    public Optional<WeeklyDietEntity> getByWeeklyId(Long weeklyId) {
        return weeklyDietRepository.findById(weeklyId);
    }

    @Override
    public List<WeeklyDietEntity> getAll() {
        return weeklyDietRepository.findAll();
    }

    @Override
    public void delete(Long weeklyId) {
        if (weeklyDietRepository.existsById(weeklyId)) {
            weeklyDietRepository.deleteById(weeklyId);
        } else {
            throw new RuntimeException(weeklyId + "삭제하지 못했습니다.");
        }
    }
}
