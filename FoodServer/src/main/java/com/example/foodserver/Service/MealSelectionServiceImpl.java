package com.example.foodserver.Service;

import com.example.foodserver.DAO.MealSelectionDAO;
import com.example.foodserver.DTO.MealSelectionDTO;
import com.example.foodserver.DTO.MealSelectionRequestDTO;
import com.example.foodserver.Entity.MealSelectionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealSelectionServiceImpl implements MealSelectionService {

    private final MealSelectionDAO mealSelectionDAO;
    private final CommunicationService communicationService;

    @Autowired
    public MealSelectionServiceImpl(MealSelectionDAO mealSelectionDAO,
                                    CommunicationService communicationService) {
        this.mealSelectionDAO = mealSelectionDAO;
        this.communicationService = communicationService;
    }

    @Override
    public MealSelectionDTO getById(Long mealSelectionId) {
        return convertToMealSelectionDTO(mealSelectionDAO.getById(mealSelectionId));
    }

    public MealSelectionEntity convertToMealSelectionEntity(MealSelectionRequestDTO mealSelectionDTO) {
        return MealSelectionEntity.builder()
                .userEmail(mealSelectionDTO.getUserEmail())
                .foodMenuId(mealSelectionDTO.getFoodMenuId())
                .mealTime(mealSelectionDTO.getMealTime())
                .count(mealSelectionDTO.getCount())
                .build();
    }

    public MealSelectionDTO convertToMealSelectionDTO(MealSelectionEntity mealSelectionEntity) {
        return MealSelectionDTO.builder()
                .mealSelectionId(mealSelectionEntity.getMealSelectionId())
                .userEmail(mealSelectionEntity.getUserEmail())
                .foodMenu(communicationService.getFoodMenu(mealSelectionEntity.getFoodMenuId()))
                .mealTime(mealSelectionEntity.getMealTime())
                .count(mealSelectionEntity.getCount())
                .build();
    }

    @Override
    public List<MealSelectionEntity> convertToMealSelectionEntities(List<MealSelectionRequestDTO> mealSelectionDTOS){
        return mealSelectionDTOS.stream().map(this::convertToMealSelectionEntity).collect(Collectors.toList());
    }

    @Override
    public List<MealSelectionDTO> convertToMealSelectionDTOS(List<MealSelectionEntity> mealSelectionEntities){
        return mealSelectionEntities.stream().map(this::convertToMealSelectionDTO).collect(Collectors.toList());
    }
}