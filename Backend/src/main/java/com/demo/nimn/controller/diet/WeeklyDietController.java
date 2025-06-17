package com.demo.nimn.controller.diet;

import com.demo.nimn.dto.diet.Response.WeeklyDietDTO;
import com.demo.nimn.dto.diet.Request.WeeklyDietRequestDTO;
import com.demo.nimn.service.diet.DietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userMeal/weekly")
public class WeeklyDietController {

    private final DietService dietService;

    @Autowired
    public WeeklyDietController(DietService dietService) {
        this.dietService = dietService;
    }

    @PostMapping("/create") // 일주일 식단 생성
    public WeeklyDietDTO createWeeklyDiet(@RequestBody WeeklyDietRequestDTO weeklyDietDTO) {
        return dietService.createWeeklyDiet(weeklyDietDTO);
    }

    @GetMapping("/read/{userEmail}") // 특정 일주일 식단 조회
    public WeeklyDietDTO getWeeklyByUserEmail(@PathVariable("userEmail") String userEmail) {
        return dietService.getWeeklyDietByUserEmail(userEmail);
    }
}