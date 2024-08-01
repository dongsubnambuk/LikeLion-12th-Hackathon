package com.example.foodserver.Controller;

import com.example.foodserver.DTO.MealSelectionDTO;
import com.example.foodserver.Service.MealSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/selections")
public class MealSelectionController {

    private final MealSelectionService mealSelectionService;

    @Autowired
    public MealSelectionController(MealSelectionService mealSelectionService) {
        this.mealSelectionService = mealSelectionService;
    }

    @PostMapping("/create") // 식사 선택 항목 생성...
    public MealSelectionDTO createMealSelection(@RequestBody MealSelectionDTO mealSelectionDTO) {
        return mealSelectionService.createMealSelection(mealSelectionDTO);
    }

    @GetMapping("/{id}")
    public MealSelectionDTO getMealSelectionById(@PathVariable Long id) {
        return mealSelectionService.getMealSelectionById(id)
                .orElseThrow(() -> new RuntimeException("MealSelection not found with id " + id));
    }

    @GetMapping("/user/{userId}") // 사용자 식단 조회
    public List<MealSelectionDTO> getMealSelectionsByUserId(@PathVariable Long userId) {
        return mealSelectionService.getMealSelectionsByUserId(userId);
    }

    @GetMapping("/daily-diet/{dailyDietId}")
    public List<MealSelectionDTO> getMealSelectionsByDailyDietId(@PathVariable Long dailyDietId) {
        return mealSelectionService.getMealSelectionsByDailyDietId(dailyDietId);
    }

    @GetMapping("/meal-time/{mealTime}")
    public List<MealSelectionDTO> getMealSelectionsByMealTime(@PathVariable String mealTime) {
        return mealSelectionService.getMealSelectionsByMealTime(mealTime);
    }

    @PutMapping("/{id}")
    public MealSelectionDTO updateMealSelection(@PathVariable Long id, @RequestBody MealSelectionDTO mealSelectionDTO) {
        return mealSelectionService.updateMealSelection(id, mealSelectionDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMealSelection(@PathVariable Long id) {
        mealSelectionService.deleteMealSelection(id);
    }
}
