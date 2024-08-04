package com.example.foodserver.Service;

import com.example.foodserver.DAO.WeeklyDietDAO;
import com.example.foodserver.DTO.WeeklyDietDTO;
import com.example.foodserver.DTO.WeeklyDietRequestDTO;
import com.example.foodserver.Entity.WeeklyDietEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public WeeklyDietDTO createWeeklyDiet(WeeklyDietRequestDTO weeklyDietDTO) {
        WeeklyDietEntity entity = convertToWeeklyDietEntity(weeklyDietDTO);
        weeklyDietDAO.create(entity);
        return convertToWeeklyDietDTO(entity);
    }

    @Override
    public WeeklyDietDTO getWeeklyDietByUserEmail(String userEmail) {
        return convertToWeeklyDietDTO(weeklyDietDAO.getByUserEmail(userEmail));
    }

    public WeeklyDietEntity convertToWeeklyDietEntity(WeeklyDietRequestDTO weeklyDietDTO) {
        return WeeklyDietEntity.builder()
                .userEmail(weeklyDietDTO.getUserEmail())
                .startDate(weeklyDietDTO.getStartDate())
                .endDate(weeklyDietDTO.getEndDate())
                .dailyDiets(dailyDietService.convertToDailyDietEntities(weeklyDietDTO.getDailyDiets()))
                .build();
    }

    public WeeklyDietDTO convertToWeeklyDietDTO(WeeklyDietEntity weeklyDietEntity) {
        return WeeklyDietDTO.builder()
                .weeklyId(weeklyDietEntity.getWeeklyId())
                .userEmail(weeklyDietEntity.getUserEmail())
                .startDate(weeklyDietEntity.getStartDate())
                .endDate(weeklyDietEntity.getEndDate())
                .dailyDiets(dailyDietService.convertToDailyDietDTOS(weeklyDietEntity.getDailyDiets()))
                .build();
    }
}