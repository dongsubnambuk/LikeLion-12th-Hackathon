package com.example.foodserver.Controller;

import com.example.foodserver.DTO.DailyDietDTO;
import com.example.foodserver.Service.DailyDietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/daily")
public class DailyDietController {

    private final DailyDietService dailyDietService;

    @Autowired
    public DailyDietController(DailyDietService dailyDietService) {
        this.dailyDietService = dailyDietService;
    }

    @PostMapping("/create") // 요일 식단 셍상
    public DailyDietDTO createDailyDiet(@RequestBody DailyDietDTO dailyDietDTO) {
       DailyDietDTO createdDietDTO;
        createdDietDTO = dailyDietService.createDailyDiet(dailyDietDTO);
        return createdDietDTO;
    }

    @GetMapping// 모든 요일 식단 조회
    public List<DailyDietDTO> getAllDailyDiets() {
        return dailyDietService.getAllDailyDiets();
    }

    @GetMapping("/{id}") // 특정 요일 식단 조회
    public DailyDietDTO getDailyDietById(@PathVariable Long id) {
        return dailyDietService.getDailyDietById(id)
                .orElseThrow(() -> new RuntimeException("DailyDiet not found with id " + id));
    }

    @DeleteMapping("/{id}") // 특정 요일 식단 삭제
    public void deleteDailyDiet(@PathVariable Long id) {
        dailyDietService.deleteDailyDiet(id);
    }
}
