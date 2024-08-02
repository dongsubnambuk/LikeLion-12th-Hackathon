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

    @GetMapping // 전체 요일 식단 조회
    public List<DailyDietDTO> getAllDailyDiets() {
        return dailyDietService.getAll();
    }

    @GetMapping("read/{dailyId}") // 특정 요일 식단 조회
    public DailyDietDTO getDailyDietById(@PathVariable Long dailyId) {
        return dailyDietService.getDailyDietById(dailyId)
                .orElseThrow(() -> new RuntimeException("DailyDiet not found with id " + dailyId));
    }

    @PostMapping("/create") // 요일 식단 생성
    public DailyDietDTO createDailyDiet(@RequestBody DailyDietDTO dailyDietDTO) {
        return dailyDietService.create(dailyDietDTO);
    }

    @GetMapping("read/dayByDay") // 주어진 요일에 대한 그 요일의 전체 식단 조회
    public List<DailyDietDTO> getByDayOfWeek(@RequestParam String dayOfWeek) {
        return dailyDietService.getByDayOfWeek(dayOfWeek);
        // http://localhost:8080/daily/read/dayByDay?dayOfWeek=월요일 로 사용해야 됨
    }

    @PutMapping("update/{dailyId}") // 특정 요일 식단 업데이트
    public DailyDietDTO updateDailyDiet(@PathVariable Long dailyId, @RequestBody DailyDietDTO dailyDietDTO) {
        return dailyDietService.update(dailyId, dailyDietDTO);
    }

    @DeleteMapping("delete/{dailyId}") // 특정 요일 식단 삭제
    public void deleteDailyDiet(@PathVariable Long dailyId) {
        dailyDietService.delete(dailyId);
    }
}
