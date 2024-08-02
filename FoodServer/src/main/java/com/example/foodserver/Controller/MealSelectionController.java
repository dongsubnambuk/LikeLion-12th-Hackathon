package com.example.foodserver.Controller;

import com.example.foodserver.DTO.MealSelectionDTO;
import com.example.foodserver.Service.MealSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/selections")
public class MealSelectionController {

    private final MealSelectionService mealSelectionService;

    @Autowired
    public MealSelectionController(MealSelectionService mealSelectionService) {
        this.mealSelectionService = mealSelectionService;
    }

    @PostMapping("/create") // 식단 조회
    public MealSelectionDTO create(@RequestBody MealSelectionDTO mealSelectionDTO) {
        return mealSelectionService.create(mealSelectionDTO);
    }

    @GetMapping("read/{mealSelectionId}") // 특정 식단 조회
    public Optional<MealSelectionDTO> getById(@PathVariable Long mealSelectionId) {
        return mealSelectionService.getById(mealSelectionId);
    }

    @GetMapping("read/All") // 전체 식단 불러오기
    public List<MealSelectionDTO> getAll() {
        return mealSelectionService.getAll();
    }

    @GetMapping("read/user/{userId}") // 사용자 식단 조회
    public List<MealSelectionDTO> getByUserId(@PathVariable Long userId) {
        return mealSelectionService.getByUserId(userId);
    }

    @GetMapping("read/daily-diet/{dailyDietId}") // 특정 일일 식단에 대한 식사 선택 항목 조회
    public List<MealSelectionDTO> getByDailyDietId(@PathVariable Long dailyDietId) {
        return mealSelectionService.getByDailyDietId(dailyDietId);
    }

    @GetMapping("read/meal-time/{mealTime}") // 특정 식사 시간 (아점저)에 대한 식사 선택 항목 조회
    public List<MealSelectionDTO> getByMealTime(@PathVariable String mealTime) {
        return mealSelectionService.getByMealTime(mealTime);
    }

    @PutMapping("/update/{mealSelectionId}") //  특정 id의 식사 선택 항목 업데이트
    public MealSelectionDTO update(@PathVariable Long mealSelectionId, @RequestBody MealSelectionDTO mealSelectionDTO) {
        return mealSelectionService.update(mealSelectionId, mealSelectionDTO);
    }

    @DeleteMapping("/delete/{mealSelectionId}") // 특정 id의 식사 선택 항목 삭제
    public void delete(@PathVariable Long mealSelectionId) {
        mealSelectionService.delete(mealSelectionId);
    }
}
