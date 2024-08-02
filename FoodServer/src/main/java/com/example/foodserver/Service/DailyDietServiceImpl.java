package com.example.foodserver.Service;

import com.example.foodserver.DAO.DailyDietDAO;
import com.example.foodserver.DTO.DailyDietDTO;
import com.example.foodserver.Entity.DailyDietEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DailyDietServiceImpl implements DailyDietService {

    private final DailyDietDAO dailyDietDAO;

    @Autowired
    public DailyDietServiceImpl(DailyDietDAO dailyDietDAO) {
        this.dailyDietDAO = dailyDietDAO;
    }

    @Override
    public DailyDietDTO create(DailyDietDTO dailyDietDTO) {
        DailyDietEntity entity = convertToEntity(dailyDietDTO);
        DailyDietEntity createdEntity = dailyDietDAO.create(entity);
        return convertToDTO(createdEntity);
    }

    @Override
    public Optional<DailyDietDTO> getDailyDietById(Long dailyDietId) {
        return dailyDietDAO.getByDailyId(dailyDietId).map(this::convertToDTO);
    }

    @Override
    public List<DailyDietDTO> getAll() {
        return dailyDietDAO.getAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public DailyDietDTO update(Long dailyId, DailyDietDTO dailyDietDTO) {
        DailyDietEntity entity = convertToEntity(dailyDietDTO);
        DailyDietEntity updatedEntity = dailyDietDAO.update(dailyId, entity);
        return convertToDTO(updatedEntity);
    }

    @Override
    public void delete(Long dailyDietId) {
        dailyDietDAO.delete(dailyDietId);
    }

    @Override
    public List<DailyDietDTO> getByDayOfWeek(String dayOfWeek) {
        return dailyDietDAO.getByDayOfWeek(dayOfWeek).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private DailyDietEntity convertToEntity(DailyDietDTO dto) {
        return DailyDietEntity.builder()
                .dayOfWeek(dto.getDayOfWeek())
                .foodMenuId(dto.getFoodMenuId())
                .build();
    }

    private DailyDietDTO convertToDTO(DailyDietEntity entity) {
        return DailyDietDTO.builder()
                .dailyId(entity.getDailyId())
                .dayOfWeek(entity.getDayOfWeek())
                .foodMenuId(entity.getFoodMenuId())
                .build();
    }
}
