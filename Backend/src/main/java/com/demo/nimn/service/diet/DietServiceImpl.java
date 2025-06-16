package com.demo.nimn.service.diet;

import com.demo.nimn.dao.diet.DailyDietDAO;
import com.demo.nimn.dao.diet.WeeklyDietDAO;
import com.demo.nimn.dto.diet.Request.UserDailyMealPlanDTO;
import com.demo.nimn.dto.diet.Request.WeeklyDietRequestDTO;
import com.demo.nimn.dto.diet.Response.DailyDietDTO;
import com.demo.nimn.dto.diet.Request.DailyDietRequestDTO;
import com.demo.nimn.dto.diet.Response.WeeklyDietDTO;
import com.demo.nimn.entity.diet.DailyDiet;
import com.demo.nimn.entity.diet.FoodSelection;
import com.demo.nimn.entity.diet.WeeklyDiet;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DietServiceImpl implements DietService {

    private final DailyDietDAO dailyDietDAO;
    private final WeeklyDietDAO weeklyDietDAO;
    private final FoodSelectionService foodSelectionService;

    @Autowired
    public DietServiceImpl(DailyDietDAO dailyDietDAO,
                           FoodSelectionService foodSelectionService,
                           WeeklyDietDAO weeklyDietDAO
                                ) {
        this.dailyDietDAO = dailyDietDAO;
        this.weeklyDietDAO = weeklyDietDAO;
        this.foodSelectionService = foodSelectionService;
    }
    //DailyDiet
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
                .foodSelections(foodSelectionService.convertToMealSelectionEntities(dailyDietDTO.getMealSelections()))
                .date(dailyDietDTO.getDate())
                .build();
    }

    private DailyDietDTO convertToDailyDietDTO(DailyDiet dailyDiet) {
        return DailyDietDTO.builder()
                .dailyDietId(dailyDiet.getId())
                .date(dailyDiet.getDate())
                .userEmail(dailyDiet.getUserEmail())
                .mealSelections(foodSelectionService.convertToMealSelectionDTOS(dailyDiet.getFoodSelections()))
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


    //WeeklyDiet
    @Override
    @Transactional
    public WeeklyDietDTO createWeeklyDiet(WeeklyDietRequestDTO weeklyDietDTO) {
        if(weeklyDietDAO.existsByCurrentWeeklyMealPlan(weeklyDietDTO.getStartDate(), weeklyDietDTO.getUserEmail())){
            return null;
        }
        WeeklyDiet entity = convertToWeeklyDietEntity(weeklyDietDTO);
        weeklyDietDAO.create(entity);
        return convertToWeeklyDietDTO(entity);
    }

    @Override
    public WeeklyDietDTO getWeeklyDietByUserEmail(String userEmail) {
        return convertToWeeklyDietDTO(weeklyDietDAO.getByUserEmail(userEmail));
    }

    public WeeklyDiet convertToWeeklyDietEntity(WeeklyDietRequestDTO weeklyDietDTO) {
        return WeeklyDiet.builder()
                .userEmail(weeklyDietDTO.getUserEmail())
                .startDate(weeklyDietDTO.getStartDate())
                .endDate(weeklyDietDTO.getEndDate())
                .dailyDiets(convertToDailyDietEntities(weeklyDietDTO.getDailyDiets()))
                .build();
    }

    public WeeklyDietDTO convertToWeeklyDietDTO(WeeklyDiet weeklyDiet) {
        return WeeklyDietDTO.builder()
                .weeklyId(weeklyDiet.getId())
                .userEmail(weeklyDiet.getUserEmail())
                .startDate(weeklyDiet.getStartDate())
                .endDate(weeklyDiet.getEndDate())
                .dailyDiets(convertToDailyDietDTOS(weeklyDiet.getDailyDiets()))
                .build();
    }

    public List<Long> convertToFoodMenuIds(DailyDiet dailyDiet){
        List<Long> foodMenuIds = new ArrayList<>();
        for(FoodSelection foodSelection : dailyDiet.getFoodSelections()) {
            foodMenuIds.add(foodSelection.getFoodMenu().getId());
        }
        return foodMenuIds;
    }

    public UserDailyMealPlanDTO convertToDailyMealPlanDTO(DailyDiet dailyDiet) {
        return UserDailyMealPlanDTO.builder()
                .date(dailyDiet.getDate())
                .foodMenuIds(convertToFoodMenuIds(dailyDiet))
                .build();
    }

    public List<UserDailyMealPlanDTO> convertToDailyMealPlanDTOS(WeeklyDiet weeklyDiet) {
        List<UserDailyMealPlanDTO> userDailyMealPlanDTOS = new ArrayList<>();
        for(DailyDiet dailyDiet : weeklyDiet.getDailyDiets()) {
            userDailyMealPlanDTOS.add(convertToDailyMealPlanDTO(dailyDiet));
        }
        return userDailyMealPlanDTOS;
    }
}