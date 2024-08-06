package com.example.foodserver.Service;

import com.example.foodserver.DAO.DailyDietDAO;
import com.example.foodserver.DTO.Response.DailyDietDTO;
import com.example.foodserver.DTO.Request.DailyDietRequestDTO;
import com.example.foodserver.Entity.DailyDietEntity;
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

    private DailyDietEntity convertToDailyDietEntity(DailyDietRequestDTO dailyDietDTO) {
        return DailyDietEntity.builder()
                .userEmail(dailyDietDTO.getUserEmail())
                .mealSelections(mealSelectionService.convertToMealSelectionEntities(dailyDietDTO.getMealSelections()))
                .date(dailyDietDTO.getDate())
                .build();
    }

    private DailyDietDTO convertToDailyDietDTO(DailyDietEntity dailyDietEntity) {
        return DailyDietDTO.builder()
                .dailyDietId(dailyDietEntity.getDailyDietId())
                .date(dailyDietEntity.getDate())
                .userEmail(dailyDietEntity.getUserEmail())
                .mealSelections(mealSelectionService.convertToMealSelectionDTOS(dailyDietEntity.getMealSelections()))
                .build();
    }

    @Override
    public List<DailyDietEntity> convertToDailyDietEntities(List<DailyDietRequestDTO> dailyDietDTOS) {
        return dailyDietDTOS.stream().map(this::convertToDailyDietEntity).collect(Collectors.toList());
    }

    @Override
    public List<DailyDietDTO> convertToDailyDietDTOS(List<DailyDietEntity> dailyDietEntities){
        return dailyDietEntities.stream().map(this::convertToDailyDietDTO).collect(Collectors.toList());
    }
}