package com.example.foodserver.Service;

import com.example.foodserver.DAO.DailyDietDAO;
import com.example.foodserver.DAO.MealSelectionDAO;
import com.example.foodserver.DTO.DailyDietDTO;
import com.example.foodserver.DTO.MealSelectionDTO;
import com.example.foodserver.Entity.DailyDietEntity;
import com.example.foodserver.Entity.MealSelectionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DailyDietServiceImpl implements DailyDietService {

    private final DailyDietDAO dailyDietDAO;
    private final MealSelectionDAO mealSelectionDAO;

    @Autowired
    public DailyDietServiceImpl(DailyDietDAO dailyDietDAO, MealSelectionDAO mealSelectionDAO) {
        this.dailyDietDAO = dailyDietDAO;
        this.mealSelectionDAO = mealSelectionDAO;
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
    public DailyDietDTO update(Long dailyDietId, DailyDietDTO dailyDietDTO) {
        DailyDietEntity entity = convertToEntity(dailyDietDTO);
        DailyDietEntity updatedEntity = dailyDietDAO.update(dailyDietId, entity);
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

    @Override
    public List<DailyDietDTO> getByUserId(Long userId) {
        return dailyDietDAO.getByUserId(userId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private DailyDietEntity convertToEntity(DailyDietDTO dto) {
        DailyDietEntity entity = DailyDietEntity.builder()
                .dayOfWeek(dto.getDayOfWeek())
                .foodMenuId(dto.getFoodMenuId())
                .userId(dto.getUserId())
                .build();

        if (dto.getMealSelections() != null) {
            List<MealSelectionEntity> mealSelections = dto.getMealSelections().stream()
                    .map(this::convertToMealSelectionEntity)
                    .collect(Collectors.toList());
            entity.setMealSelections(mealSelections);
        }

        return entity;
    }

    private DailyDietDTO convertToDTO(DailyDietEntity entity) {
        List<MealSelectionDTO> mealSelections = mealSelectionDAO.getByDailyDietId(entity.getDailyDietId()).stream()
                .map(this::convertToMealSelectionDTO)
                .collect(Collectors.toList());

        return DailyDietDTO.builder()
                .dailyDietId(entity.getDailyDietId())
                .dayOfWeek(entity.getDayOfWeek())
                .foodMenuId(entity.getFoodMenuId())
                .userId(entity.getUserId())
                .mealSelections(mealSelections)
                .build();
    }

    private MealSelectionEntity convertToMealSelectionEntity(MealSelectionDTO dto) {
        return MealSelectionEntity.builder()
                .mealSelectionId(dto.getMealSelectionId())
                .userId(dto.getUserId())
                .dailyDiet(DailyDietEntity.builder().dailyDietId(dto.getDailyDietId()).build())
                .foodMenuId(dto.getFoodMenuId())
                .mealTime(dto.getMealTime())
                .count(dto.getCount())
                .build();
    }

    private MealSelectionDTO convertToMealSelectionDTO(MealSelectionEntity entity) {
        return MealSelectionDTO.builder()
                .mealSelectionId(entity.getMealSelectionId())
                .userId(entity.getUserId())
                .dailyDietId(entity.getDailyDiet().getDailyDietId())
                .foodMenuId(entity.getFoodMenuId())
                .mealTime(entity.getMealTime())
                .count(entity.getCount())
                .build();
    }
}
