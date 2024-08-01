package com.example.foodserver.DAO;

import com.example.foodserver.DTO.DailyDietDTO;
import com.example.foodserver.Entity.DailyDietEntity;
import com.example.foodserver.Repository.DailyDietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DailyDietDAOImpl implements DailyDietDAO {

    private final DailyDietRepository dailyDietRepository;

    @Autowired
    public DailyDietDAOImpl(DailyDietRepository dailyDietRepository) {
        this.dailyDietRepository = dailyDietRepository;
    }

    @Override
    public DailyDietDTO create(DailyDietDTO dailyDietDTO) {
        DailyDietEntity entity = convertToEntity(dailyDietDTO);
        DailyDietEntity savedEntity = dailyDietRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    @Override
    public Optional<DailyDietDTO> getById(Long id) {
        return dailyDietRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<DailyDietDTO> getAll() {
        return dailyDietRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (dailyDietRepository.existsById(id)) {
            dailyDietRepository.deleteById(id);
        } else {
            throw new RuntimeException("DailyDiet not found with id " + id);
        }
    }

    private DailyDietEntity convertToEntity(DailyDietDTO dto) {
        return DailyDietEntity.builder()
                .dayOfWeek(dto.getDayOfWeek())
                .mealTime(dto.getMealTime())
                .count(dto.getCount())
                .foodMenuId(dto.getFoodMenuId())
                .build();
    }

    private DailyDietDTO convertToDTO(DailyDietEntity entity) {
        return DailyDietDTO.builder()
                .id(entity.getId())
                .dayOfWeek(entity.getDayOfWeek())
                .mealTime(entity.getMealTime())
                .count(entity.getCount())
                .foodMenuId(entity.getFoodMenuId())
                .build();
    }
}
