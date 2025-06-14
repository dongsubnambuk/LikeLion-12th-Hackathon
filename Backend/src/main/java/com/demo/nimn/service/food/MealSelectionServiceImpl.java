package com.demo.nimn.service.food;

import com.demo.nimn.dao.food.MealSelectionDAO;
import com.demo.nimn.dto.food.Response.MealSelectionDTO;
import com.demo.nimn.dto.food.Request.MealSelectionRequestDTO;
import com.demo.nimn.entity.food.MealSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealSelectionServiceImpl implements MealSelectionService {

    private final MealSelectionDAO mealSelectionDAO;

    @Autowired
    public MealSelectionServiceImpl(MealSelectionDAO mealSelectionDAO) {
        this.mealSelectionDAO = mealSelectionDAO;
    }

    @Override
    public MealSelectionDTO getById(Long mealSelectionId) {
        return convertToMealSelectionDTO(mealSelectionDAO.getById(mealSelectionId));
    }

    public MealSelection convertToMealSelectionEntity(MealSelectionRequestDTO mealSelectionDTO) {
        return MealSelection.builder()
                .userEmail(mealSelectionDTO.getUserEmail())
                .foodMenuId(mealSelectionDTO.getFoodMenuId())
                .mealTime(mealSelectionDTO.getMealTime())
                .count(mealSelectionDTO.getCount())
                .build();
    }

    public MealSelectionDTO convertToMealSelectionDTO(MealSelection mealSelection) {
        // TODO: foodMenu 조회해서 가져오는 로직 들어가야 함
        return MealSelectionDTO.builder()
                .mealSelectionId(mealSelection.getId())
                .userEmail(mealSelection.getUserEmail())
                .foodMenu(null)
                .mealTime(mealSelection.getMealTime())
                .count(mealSelection.getCount())
                .build();
    }

    @Override
    public List<MealSelection> convertToMealSelectionEntities(List<MealSelectionRequestDTO> mealSelectionDTOS){
        return mealSelectionDTOS.stream().map(this::convertToMealSelectionEntity).collect(Collectors.toList());
    }

    @Override
    public List<MealSelectionDTO> convertToMealSelectionDTOS(List<MealSelection> mealSelectionEntities){
        return mealSelectionEntities.stream().map(this::convertToMealSelectionDTO).collect(Collectors.toList());
    }
}