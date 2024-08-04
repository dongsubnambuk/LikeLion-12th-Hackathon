package com.example.foodserver.Service;

import com.example.foodserver.DTO.FoodMenuDTO;
import com.example.foodserver.DTO.UserDailyMealPlanDTO;

import java.util.List;

public interface CommunicationService {
    public String createReview(String userEmail, List<UserDailyMealPlanDTO> userDailyMealPlanDTOS);
    public FoodMenuDTO getFoodMenu(Long foodMenuId);
}