package com.demo.nimn.service.food;

import com.demo.nimn.dao.food.DailyDietDAO;
import com.demo.nimn.dto.food.Response.DailyDietDTO;
import com.demo.nimn.dto.food.Request.DailyDietRequestDTO;
import com.demo.nimn.entity.food.DailyDiet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DailyDietServiceImpl implements DailyDietService {

    private final DailyDietDAO dailyDietDAO;
    private final MealSelectionService mealSelectionService;

    @Autowired
    public DailyDietServiceImpl(DailyDietDAO dailyDietDAO,
                                MealSelectionService mealSelectionService) {
        this.dailyDietDAO = dailyDietDAO;
        this.mealSelectionService = mealSelectionService;
    }

    @Override
    public List<DailyDietDTO> getByUserEmailAndDate(String userEmail, LocalDate date) {
        return dailyDietDAO.getByUserEmailAndDate(userEmail, date).stream().map(this::convertToDailyDietDTO).collect(Collectors.toList());
    }

    @Override
    public List<DailyDietDTO> getByDate(LocalDate date){
        return dailyDietDAO.getByDate(date).stream().map(this::convertToDailyDietDTO).collect(Collectors.toList());
    }

    private DailyDiet convertToDailyDietEntity(DailyDietRequestDTO dailyDietDTO) {
        return DailyDiet.builder()
                .userEmail(dailyDietDTO.getUserEmail())
                .mealSelections(mealSelectionService.convertToMealSelectionEntities(dailyDietDTO.getMealSelections()))
                .date(dailyDietDTO.getDate())
                .build();
    }

    private DailyDietDTO convertToDailyDietDTO(DailyDiet dailyDiet) {
        return DailyDietDTO.builder()
                .dailyDietId(dailyDiet.getDailyDietId())
                .date(dailyDiet.getDate())
                .userEmail(dailyDiet.getUserEmail())
                .mealSelections(mealSelectionService.convertToMealSelectionDTOS(dailyDiet.getMealSelections()))
                .build();
    }

    @Override
    public List<DailyDiet> convertToDailyDietEntities(List<DailyDietRequestDTO> dailyDietDTOS) {
        return dailyDietDTOS.stream().map(this::convertToDailyDietEntity).collect(Collectors.toList());
    }

    @Override
    public List<DailyDietDTO> convertToDailyDietDTOS(List<DailyDiet> dailyDietEntities){
        return dailyDietEntities.stream().map(this::convertToDailyDietDTO).collect(Collectors.toList());
    }
}