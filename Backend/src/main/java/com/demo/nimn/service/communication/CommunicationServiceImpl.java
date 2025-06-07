package com.demo.nimn.service.communication;

import com.demo.nimn.dto.food.Request.UserDailyMealPlanDTO;
import com.demo.nimn.dto.food.Response.FoodMenuDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunicationServiceImpl implements CommunicationService {

    @Override
    public String imageUpload(byte[] image) {
        return "";
    }

    @Override
    public List<String> readAllUser() {
        return List.of();
    }

    @Override
    public String createReview(String userEmail, List<UserDailyMealPlanDTO> userDailyMealPlanDTOS) {
        return "";
    }

    @Override
    public FoodMenuDTO getFoodMenu(Long foodMenuId) {
        return null;
    }
}
