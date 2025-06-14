package com.demo.nimn.service.food;

import com.demo.nimn.dao.food.WeeklyDietDAO;
import com.demo.nimn.dto.food.Request.UserDailyMealPlanDTO;
import com.demo.nimn.dto.food.Request.WeeklyDietRequestDTO;
import com.demo.nimn.dto.food.Response.WeeklyDietDTO;
import com.demo.nimn.entity.food.DailyDiet;
import com.demo.nimn.entity.food.MealSelection;
import com.demo.nimn.entity.food.WeeklyDiet;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeeklyDietServiceImpl implements WeeklyDietService {

    private final WeeklyDietDAO weeklyDietDAO;
    private final DailyDietService dailyDietService;

    @Autowired
    public WeeklyDietServiceImpl(WeeklyDietDAO weeklyDietDAO,
                                 DailyDietService dailyDietService) {
        this.weeklyDietDAO = weeklyDietDAO;
        this.dailyDietService = dailyDietService;
    }

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
                .dailyDiets(dailyDietService.convertToDailyDietEntities(weeklyDietDTO.getDailyDiets()))
                .build();
    }

    public WeeklyDietDTO convertToWeeklyDietDTO(WeeklyDiet weeklyDiet) {
        return WeeklyDietDTO.builder()
                .weeklyId(weeklyDiet.getId())
                .userEmail(weeklyDiet.getUserEmail())
                .startDate(weeklyDiet.getStartDate())
                .endDate(weeklyDiet.getEndDate())
                .dailyDiets(dailyDietService.convertToDailyDietDTOS(weeklyDiet.getDailyDiets()))
                .build();
    }

    public List<Long> convertToFoodMenuIds(DailyDiet dailyDiet){
        List<Long> foodMenuIds = new ArrayList<>();
        for(MealSelection mealSelection : dailyDiet.getMealSelections()) {
            foodMenuIds.add(mealSelection.getFoodMenuId());
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