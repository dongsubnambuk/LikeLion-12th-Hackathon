package com.example.foodserver.Service;

import com.example.foodserver.DAO.MealSelectionDAO;
import com.example.foodserver.DTO.MealSelectionDTO;
import com.example.foodserver.Entity.MealSelectionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MealSelectionServiceImpl implements MealSelectionService {

    private final MealSelectionDAO mealSelectionDAO;

    @Autowired
    public MealSelectionServiceImpl(MealSelectionDAO mealSelectionDAO) {
        this.mealSelectionDAO = mealSelectionDAO;
    }

    @Override
    public MealSelectionDTO create(MealSelectionDTO mealSelectionDTO) {
        MealSelectionEntity entity = convertToEntity(mealSelectionDTO);
        MealSelectionEntity savedEntity = mealSelectionDAO.create(entity);
        return convertToDTO(savedEntity);
    }

    @Override
    public Optional<MealSelectionDTO> getById(Long mealSelectionId) {
        return mealSelectionDAO.getById(mealSelectionId).map(this::convertToDTO);
    }

    @Override
    public List<MealSelectionDTO> getAll() {
        return mealSelectionDAO.getAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MealSelectionDTO> getByUserId(Long userId) {
        return mealSelectionDAO.getByUserId(userId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MealSelectionDTO> getByDailyDietId(Long dailyDietId) {
        return mealSelectionDAO.getByDailyDietId(dailyDietId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<MealSelectionDTO> getByMealTime(String mealTime) {
        return mealSelectionDAO.getByMealTime(mealTime).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public MealSelectionDTO update(Long mealSelectionId, MealSelectionDTO mealSelectionDTO) {
        MealSelectionEntity entity = convertToEntity(mealSelectionDTO);
        MealSelectionEntity updatedEntity = mealSelectionDAO.update(mealSelectionId, entity);
        return convertToDTO(updatedEntity);
    }

    @Override
    public void delete(Long mealSelectionId) {
        mealSelectionDAO.delete(mealSelectionId);
    }

    private MealSelectionEntity convertToEntity(MealSelectionDTO dto) {
        return MealSelectionEntity.builder()
                .mealSelectionId(dto.getMealSelectionId()) // Add this line to set mealSelectionId
                .userId(dto.getUserId())
                .dailyDietId(dto.getDailyDietId())
                .foodMenuId(dto.getFoodMenuId())
                .mealTime(dto.getMealTime())
                .count(dto.getCount())
                .build();
    }

    private MealSelectionDTO convertToDTO(MealSelectionEntity entity) {
        return MealSelectionDTO.builder()
                .mealSelectionId(entity.getMealSelectionId())
                .userId(entity.getUserId())
                .dailyDietId(entity.getDailyDietId())
                .foodMenuId(entity.getFoodMenuId())
                .mealTime(entity.getMealTime())
                .count(entity.getCount())
                .build();
    }
}
