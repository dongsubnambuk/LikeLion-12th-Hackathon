package com.example.foodserver.DAO;

import com.example.foodserver.DTO.WeeklyDietDTO;
import com.example.foodserver.Entity.WeeklyDietEntity;
import com.example.foodserver.Repository.WeeklyDietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class WeeklyDietDAOImpl implements WeeklyDietDAO {

    private final WeeklyDietRepository weeklyDietRepository;

    @Autowired
    public WeeklyDietDAOImpl(WeeklyDietRepository weeklyDietRepository) {
        this.weeklyDietRepository = weeklyDietRepository;
    }

    @Override
    public WeeklyDietDTO create(WeeklyDietDTO weeklyDietDTO) {
        WeeklyDietEntity entity = convertToEntity(weeklyDietDTO);
        WeeklyDietEntity savedEntity = weeklyDietRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    @Override
    public Optional<WeeklyDietDTO> getById(Long id) {
        return weeklyDietRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<WeeklyDietDTO> getAll() {
        return weeklyDietRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        if (weeklyDietRepository.existsById(id)) {
            weeklyDietRepository.deleteById(id);
        } else {
            throw new RuntimeException("WeeklyDiet not found with id " + id);
        }
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
                .id(entity.getId())
                .userId(entity.getUserId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }
}
