package com.demo.nimn.service.food;

import com.example.foodserver.DTO.Response.FoodMenuDTO;
import com.example.foodserver.DTO.Request.UserDailyMealPlanDTO;

import java.util.List;

public interface CommunicationService {
    public String createReview(String userEmail, List<UserDailyMealPlanDTO> userDailyMealPlanDTOS);
    public FoodMenuDTO getFoodMenu(Long foodMenuId);
}