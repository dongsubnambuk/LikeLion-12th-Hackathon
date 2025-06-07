package com.demo.nimn.controller.food;

import com.example.foodserver.DTO.Response.WeeklyDietDTO;
import com.example.foodserver.DTO.Request.WeeklyDietRequestDTO;
import com.example.foodserver.Service.WeeklyDietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userMeal/weekly")
public class WeeklyDietController {

    private final WeeklyDietService weeklyDietService;

    @Autowired
    public WeeklyDietController(WeeklyDietService weeklyDietService) {
        this.weeklyDietService = weeklyDietService;
    }

    @PostMapping("/create") // 일주일 식단 생성
    public WeeklyDietDTO createWeeklyDiet(@RequestBody WeeklyDietRequestDTO weeklyDietDTO) {
        return weeklyDietService.createWeeklyDiet(weeklyDietDTO);
    }

    @GetMapping("/read/{userEmail}") // 특정 일주일 식단 조회
    public WeeklyDietDTO getWeeklyByUserEmail(@PathVariable("userEmail") String userEmail) {
        return weeklyDietService.getWeeklyDietByUserEmail(userEmail);
    }
}