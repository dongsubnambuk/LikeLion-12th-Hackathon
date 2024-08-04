package com.example.foodserver.Service;

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
public class MealSelectionServiceImpl implements MealSelectionService {

    private final MealSelectionDAO mealSelectionDAO;
    private final DailyDietService dailyDietService;

    @Autowired
    public MealSelectionServiceImpl(MealSelectionDAO mealSelectionDAO, DailyDietService dailyDietService) {
        this.mealSelectionDAO = mealSelectionDAO;
        this.dailyDietService = dailyDietService;
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

    @Override
    public List<DailyDietDTO> createWeeklyDiet(Long userId, List<DailyDietDTO> dailyDiets) {
        return dailyDiets.stream().map(dailyDietDTO -> {
            dailyDietDTO.setUserId(userId);
            return dailyDietService.create(dailyDietDTO);
        }).collect(Collectors.toList());
    }

    private MealSelectionEntity convertToEntity(MealSelectionDTO dto) {
        DailyDietEntity dailyDietEntity = dailyDietService.getDailyDietById(dto.getDailyDietId())
                .map(this::convertToEntity)  // Convert DTO to Entity
                .orElse(null);

        return MealSelectionEntity.builder()
                .mealSelectionId(dto.getMealSelectionId())
                .userId(dto.getUserId())
                .dailyDiet(dailyDietEntity)  // Set the DailyDietEntity
                .foodMenuId(dto.getFoodMenuId())
                .mealTime(dto.getMealTime())
                .count(dto.getCount())
                .build();
    }

    private DailyDietEntity convertToEntity(DailyDietDTO dto) {
        return DailyDietEntity.builder()
                .dailyDietId(dto.getDailyDietId())
                .dayOfWeek(dto.getDayOfWeek())
                .foodMenuId(dto.getFoodMenuId())
                .build();
    }

    private MealSelectionDTO convertToDTO(MealSelectionEntity entity) {
        return MealSelectionDTO.builder()
                .mealSelectionId(entity.getMealSelectionId())
                .userId(entity.getUserId())
                .dailyDietId(entity.getDailyDiet() != null ? entity.getDailyDiet().getDailyDietId() : null)
                .foodMenuId(entity.getFoodMenuId())
                .mealTime(entity.getMealTime())
                .count(entity.getCount())
                .build();
    }
}
