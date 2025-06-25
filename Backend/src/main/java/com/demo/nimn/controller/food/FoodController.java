package com.demo.nimn.controller.food;

import com.demo.nimn.dto.food.FoodDTO;
import com.demo.nimn.dto.food.WeeklyFoodPlanDTO;
import com.demo.nimn.service.food.FoodService;
import com.demo.nimn.service.food.WeeklyFoodPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "FOOD SERVER API", description = "AI를 사용한 식단 및 이미지 자동 생성")
@RestController
@RequestMapping(value = "/foods")
public class FoodController {
    private final FoodService foodService;
    private final WeeklyFoodPlanService weeklyFoodPlanService;

    @Autowired
    public FoodController(FoodService foodService, WeeklyFoodPlanService weeklyFoodPlanService) {
        this.foodService = foodService;
        this.weeklyFoodPlanService = weeklyFoodPlanService;
    }

    @Operation(summary = "음식 생성(CreateFood)", description = "가격을 입력하면 가격에 맞는 영양 있는 음식 생성")
    @PostMapping(value = "/food")
    public FoodDTO createFood(@RequestParam(name = "price") String price) {
        return foodService.createFood(price);
    }

    @Operation(summary = "한 주 식단 선택지 생성(CreateWeeklyFoodPlan)", description = "한 주 식단 선택지 생성")
    @PostMapping(value = "/food-plans/weekly")
    public WeeklyFoodPlanDTO createWeeklyFoodPlan() {
        return weeklyFoodPlanService.createWeeklyPlan();
    }

    @Operation(summary = "한 주 식단 선택지 조회(ReadWeeklyFoodPlan)", description = "한 주 식단 선택지 조회")
    @GetMapping(value = "/food-plans/weekly")
    public WeeklyFoodPlanDTO readWeeklyFoodPlan() {
        return weeklyFoodPlanService.readWeeklyFoodPlan();
    }

    @Operation(summary = "모든 식단 선택지 조회(ReadAllFood)", description = "모든 식단 선택지 조회")
    @GetMapping(value = "/food")
    public List<FoodDTO> readAllFood() {
        return foodService.readAll();
    }

    @Operation(summary = "특정 음식 조회(readByFoodId)", description = "음식 ID로 특정 음식 조회")
    @GetMapping(value = "/food/{foodId}")
    public FoodDTO readByFoodId(@PathVariable("foodId") Long foodId) {
        return foodService.readFoodDTOByFoodId(foodId);
    }
}