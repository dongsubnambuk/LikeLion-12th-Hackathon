package com.example.foodserver.Service;

import com.example.foodserver.DAO.WeeklyDietDAO;
import com.example.foodserver.DTO.WeeklyDietDTO;
import com.example.foodserver.Entity.WeeklyDietEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeeklyDietServiceImpl implements WeeklyDietService {

    private final WeeklyDietDAO weeklyDietDAO;

    @Autowired
    public WeeklyDietServiceImpl(WeeklyDietDAO weeklyDietDAO) {
        this.weeklyDietDAO = weeklyDietDAO;
    }

    @Override
    public WeeklyDietDTO createWeeklyDiet(WeeklyDietDTO weeklyDietDTO) {
        WeeklyDietEntity entity = convertToEntity(weeklyDietDTO);
        WeeklyDietEntity savedEntity = weeklyDietDAO.create(entity);
        return convertToDTO(savedEntity);
    }

    @Override
    public Optional<WeeklyDietDTO> getWeeklyDietById(Long weeklyDietId) {
        return weeklyDietDAO.getByWeeklyId(weeklyDietId).map(this::convertToDTO);
    }

    @Override
    public List<WeeklyDietDTO> getAllWeeklyDiets() {
        return weeklyDietDAO.getAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteWeeklyDiet(Long weeklyId) {
        weeklyDietDAO.delete(weeklyId);
    }

    private WeeklyDietEntity convertToEntity(WeeklyDietDTO dto) {
        return WeeklyDietEntity.builder()
                .userId(dto.getUserId())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
    }

    private WeeklyDietDTO convertToDTO(WeeklyDietEntity entity) {
        return WeeklyDietDTO.builder()
                .weeklyId(entity.getWeeklyId())
                .userId(entity.getUserId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }
}
