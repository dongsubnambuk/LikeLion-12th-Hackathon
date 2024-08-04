package com.example.ai.Controller;

import com.example.ai.DTO.Meal.FoodMenuDTO;
import com.example.ai.DTO.Meal.WeeklyMealPlanDTO;
import com.example.ai.Service.Meal.MealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "MEAL SERVER API", description = "AI를 사용한 식단 및 이미지 자동 생성")
@RestController
@RequestMapping(value = "/api/meal")
public class MealController {
    private final MealService mealService;

    @Autowired
    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @Operation(summary = "식단 생성(CreateMeal)", description = "가격을 입력하면 가격에 맞는 영양 있는 식단 생성")
    @PostMapping(value = "/food-menu")
    public FoodMenuDTO createMeal(@RequestParam(name = "price") String price) {
        return mealService.createMeal(price);
    }

    @Operation(summary = "한 주 식단 생성(CreateWeeklyMealPlan)", description = "한 주 식단 생성")
    @PostMapping(value = "/meal-plans/weekly")
    public WeeklyMealPlanDTO createWeeklyMealPlan() {
        return mealService.createWeeklyMealPlan();
    }

    @Operation(summary = "한 주 식단 조회(ReadWeeklyMealPlan)", description = "한 주 식단 조회")
    @GetMapping(value = "/meal-plans/weekly")
    public WeeklyMealPlanDTO readWeeklyMealPlan() {
        return mealService.readWeeklyMealPlan();
    }

    @GetMapping(value = "/food")
    public List<FoodMenuDTO> readAllFoodMenu() {
        return mealService.readAll();
    }

    @GetMapping(value = "/food/{foodMenuId}")
    public FoodMenuDTO readByFoodMenuId(@PathVariable("foodMenuId") Long foodMenuId) {
        return mealService.readFoodMenuDTOByFoodMenuId(foodMenuId);
    }
}