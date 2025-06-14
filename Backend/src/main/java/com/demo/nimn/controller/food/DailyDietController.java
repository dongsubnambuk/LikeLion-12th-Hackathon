package com.demo.nimn.controller.food;

import com.demo.nimn.dto.food.Response.DailyDietDTO;
import com.demo.nimn.dto.food.Request.DailyRequestDTO;
import com.demo.nimn.service.food.DailyDietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/userMeal/daily")
public class DailyDietController {

    private final DailyDietService dailyDietService;

    @Autowired
    public DailyDietController(DailyDietService dailyDietService) {
        this.dailyDietService = dailyDietService;
    }

    @GetMapping("/read")
    public ResponseEntity<List<DailyDietDTO>> getDailyDietsByUserEmailAndDate(@RequestBody DailyRequestDTO dailyRequestDTO) {
        List<DailyDietDTO> dailyDiets = dailyDietService.getByUserEmailAndDate(dailyRequestDTO.getUserEmail(), dailyRequestDTO.getDate());
        return ResponseEntity.ok(dailyDiets);
    }

    @GetMapping("/read/{date}")
    public ResponseEntity<List<DailyDietDTO>> getDailyDietsByDate(@PathVariable("date") LocalDate date) {
        List<DailyDietDTO> dailyDiets = dailyDietService.getByDate(date);
        return ResponseEntity.ok(dailyDiets);
    }
}