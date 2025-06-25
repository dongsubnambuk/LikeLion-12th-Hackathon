package com.demo.nimn.controller.diet;

import com.demo.nimn.dto.diet.Response.DailyDietDTO;
import com.demo.nimn.dto.diet.Request.DailyRequestDTO;
import com.demo.nimn.service.diet.DietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/userMeal/daily")
public class DailyDietController {

    private final DietService dietService;

    @Autowired
    public DailyDietController(DietService dietService) {
        this.dietService = dietService;
    }

    // email과 date로 하루 식단 조회?
    @GetMapping("/read")
    public ResponseEntity<List<DailyDietDTO>> getDailyDietsByUserEmailAndDate(@RequestBody DailyRequestDTO dailyRequestDTO) {
        List<DailyDietDTO> dailyDiets = dietService.getByUserEmailAndDate(dailyRequestDTO.getUserEmail(), dailyRequestDTO.getDate());
        return ResponseEntity.ok(dailyDiets);
    }
    // date에 해당되는 식단 조회
    @GetMapping("/read/{date}")
    public ResponseEntity<List<DailyDietDTO>> getDailyDietsByDate(@PathVariable("date") LocalDate date) {
        List<DailyDietDTO> dailyDiets = dietService.getByDate(date);
        return ResponseEntity.ok(dailyDiets);
    }
}