package com.example.foodserver.Controller;

import com.example.foodserver.DTO.WeeklyDietDTO;
import com.example.foodserver.Service.WeeklyDietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weekly")
public class WeeklyDietController {

    private final WeeklyDietService weeklyDietService;

    @Autowired
    public WeeklyDietController(WeeklyDietService weeklyDietService) {
        this.weeklyDietService = weeklyDietService;
    }

    @PostMapping("/create") // 일주일 식단 생성
    public WeeklyDietDTO createWeeklyDiet(@RequestBody WeeklyDietDTO weeklyDietDTO) {
        return weeklyDietService.createWeeklyDiet(weeklyDietDTO);
    }

    @GetMapping("read/all") // 전체 조회
    public List<WeeklyDietDTO> getAllWeeklyDiets() {
        return weeklyDietService.getAllWeeklyDiets();
    }

    @GetMapping("read/{weeklyId}") // 특정 일주일 식단 조회
    public WeeklyDietDTO getWeeklyDietById(@PathVariable Long weeklyId) {
        return weeklyDietService.getWeeklyDietById(weeklyId)
                .orElseThrow(() -> new RuntimeException(weeklyId + "특정 식단을 조회하지 못했습니다."));
    }

    @DeleteMapping("delete/{weeklyId}") // id ( 특정 일주일 식단 삭제)
    public void deleteWeeklyDiet(@PathVariable Long weeklyId) {
        weeklyDietService.deleteWeeklyDiet(weeklyId);
    }
}