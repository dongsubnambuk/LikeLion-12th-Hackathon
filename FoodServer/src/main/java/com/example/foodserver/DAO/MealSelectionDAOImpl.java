package com.example.foodserver.DAO;

import com.example.foodserver.DTO.MealSelectionDTO;
import com.example.foodserver.Entity.MealSelectionEntity;
import com.example.foodserver.Repository.MealSelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MealSelectionDAOImpl implements MealSelectionDAO {

    private final MealSelectionRepository mealSelectionRepository;

    @Autowired
    public MealSelectionDAOImpl(MealSelectionRepository mealSelectionRepository) {
        this.mealSelectionRepository = mealSelectionRepository;
    }

    @Override
    public MealSelectionDTO create(MealSelectionDTO mealSelectionDTO) {
        MealSelectionEntity entity = convertToEntity(mealSelectionDTO);
        MealSelectionEntity savedEntity = mealSelectionRepository.save(entity);
        return convertToDTO(savedEntity);
    }

    @Override
    public Optional<MealSelectionDTO> getById(Long id) {
        return mealSelectionRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public List<MealSelectionDTO> getAll() {
        return mealSelectionRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MealSelectionDTO> getByUserId(Long userId) {
        return mealSelectionRepository.findByUserId(userId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MealSelectionDTO> getByDailyDietId(Long dailyDietId) {
        return mealSelectionRepository.findByDailyDietId(dailyDietId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MealSelectionDTO> getByMealTime(String mealTime) {
        return mealSelectionRepository.findByMealTime(mealTime).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public MealSelectionDTO update(Long id, MealSelectionDTO mealSelectionDTO) {
        if (mealSelectionRepository.existsById(id)) {
            MealSelectionEntity entity = convertToEntity(mealSelectionDTO);
            entity.setId(id); // Ensure the ID is set for the update
            MealSelectionEntity updatedEntity = mealSelectionRepository.save(entity);
            return convertToDTO(updatedEntity);
        } else {
            throw new RuntimeException("MealSelection not found with id " + id);
        }
    }

    @Override
    public void delete(Long id) {
        if (mealSelectionRepository.existsById(id)) {
            mealSelectionRepository.deleteById(id);
        } else {
            throw new RuntimeException("MealSelection not found with id " + id);
        }
    }

    private MealSelectionEntity convertToEntity(MealSelectionDTO dto) {
        return MealSelectionEntity.builder()
                .userId(dto.getUserId())
                .dailyDietId(dto.getDailyDietId())
                .foodMenuId(dto.getFoodMenuId())
                .mealTime(dto.getMealTime())
                .count(dto.getCount())
                .build();
    }

    private MealSelectionDTO convertToDTO(MealSelectionEntity entity) {
        return MealSelectionDTO.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .dailyDietId(entity.getDailyDietId())
                .foodMenuId(entity.getFoodMenuId())
                .mealTime(entity.getMealTime())
                .count(entity.getCount())
                .build();
    }
}
