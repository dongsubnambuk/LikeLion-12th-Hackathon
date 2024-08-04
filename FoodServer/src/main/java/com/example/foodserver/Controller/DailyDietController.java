package com.example.foodserver.Controller;

import com.example.foodserver.DTO.DailyDietDTO;
import com.example.foodserver.DTO.DailyRequestDTO;
import com.example.foodserver.Service.DailyDietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userMeal/daily")
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
}