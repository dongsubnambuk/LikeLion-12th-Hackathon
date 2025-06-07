package com.demo.nimn.service.communication;

import com.demo.nimn.dto.food.Request.UserDailyMealPlanDTO;
import com.demo.nimn.dto.food.Response.FoodMenuDTO;

import java.util.List;

public interface CommunicationService {
    public String imageUpload(byte[] image);
    public List<String> readAllUser();
    public String createReview(String userEmail, List<UserDailyMealPlanDTO> userDailyMealPlanDTOS);
    public FoodMenuDTO getFoodMenu(Long foodMenuId);
}
