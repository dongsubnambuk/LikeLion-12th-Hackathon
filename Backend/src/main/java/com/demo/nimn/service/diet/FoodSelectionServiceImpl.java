package com.demo.nimn.service.diet;

import com.demo.nimn.dao.diet.MealSelectionDAO;
import com.demo.nimn.dao.meal.FoodDAO;
import com.demo.nimn.dto.diet.Response.FoodSelectionDTO;
import com.demo.nimn.dto.diet.Request.FoodSelectionRequestDTO;
import com.demo.nimn.entity.diet.FoodSelection;
import com.demo.nimn.entity.meal.FoodMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodSelectionServiceImpl implements FoodSelectionService {

    private final MealSelectionDAO mealSelectionDAO;
    private final FoodDAO foodDAO;

    @Autowired
    public FoodSelectionServiceImpl(MealSelectionDAO mealSelectionDAO, FoodDAO foodDAO) {
        this.mealSelectionDAO = mealSelectionDAO;
        this.foodDAO = foodDAO;
    }

    @Override
    public FoodSelectionDTO getById(Long mealSelectionId) {
        return convertToMealSelectionDTO(mealSelectionDAO.getById(mealSelectionId));
    }

    public FoodSelection convertToMealSelectionEntity(FoodSelectionRequestDTO mealSelectionDTO) {
        FoodMenu foodMenu = foodDAO.findById(mealSelectionDTO.getFoodMenuId());

        return FoodSelection.builder()
                .userEmail(mealSelectionDTO.getUserEmail())
                .foodMenu(foodMenu)
                .mealTime(mealSelectionDTO.getFoodTime())
                .count(mealSelectionDTO.getCount())
                .build();
    }

    public FoodSelectionDTO convertToMealSelectionDTO(FoodSelection foodSelection) {
        // TODO: foodMenu 조회해서 가져오는 로직 들어가야 함
        return FoodSelectionDTO.builder()
                .foodSelectionId(foodSelection.getId())
                .userEmail(foodSelection.getUserEmail())
                .foodMenu(null)
                .foodTime(foodSelection.getMealTime())
                .count(foodSelection.getCount())
                .build();
    }

    @Override
    public List<FoodSelection> convertToMealSelectionEntities(List<FoodSelectionRequestDTO> mealSelectionDTOS){
        return mealSelectionDTOS.stream().map(this::convertToMealSelectionEntity).collect(Collectors.toList());
    }

    @Override
    public List<FoodSelectionDTO> convertToMealSelectionDTOS(List<FoodSelection> foodSelectionEntities){
        return foodSelectionEntities.stream().map(this::convertToMealSelectionDTO).collect(Collectors.toList());
    }
}