package com.demo.nimn.controller.diet;

import com.demo.nimn.dto.diet.FoodNutritionDTO;
import com.demo.nimn.service.diet.FoodMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodMenuController {

    private final FoodMenuService foodMenuService;

    @Autowired
    public FoodMenuController(FoodMenuService foodMenuService) {
        this.foodMenuService = foodMenuService;
    }

    @PostMapping("/create") // 메뉴 생성
    public FoodNutritionDTO createFoodMenu(@RequestBody FoodNutritionDTO foodNutritionDTO) {
        return foodMenuService.createFoodMenu(foodNutritionDTO);
    }

    @GetMapping("/{id}") // 특정 메뉴 찾기 (불고기 정식 이런 거)
    public FoodNutritionDTO getFoodMenuById(@PathVariable Long id) {
        return foodMenuService.getFoodMenuById(id)
                .orElseThrow(() -> new RuntimeException(id + " 메뉴를 찾지 못했습니다."));
    }

    @GetMapping("/all") // 메뉴 전체 불러오기
    public List<FoodNutritionDTO> getAllFoodMenus() {
        return foodMenuService.getAllFoodMenus();
    }

    @PutMapping("/{id}") // 특정 메뉴 세부 정보 바뀌면 데이터 업뎃할 수 잇게...
    public FoodNutritionDTO updateFoodMenu(@PathVariable Long id, @RequestBody FoodNutritionDTO foodNutritionDTO) {
        return foodMenuService.updateFoodMenu(id, foodNutritionDTO);
    }

    @DeleteMapping("/{id}") // 메뉴 삭제git remote -vgit remote -v
    public void deleteFoodMenu(@PathVariable Long id) {
        foodMenuService.deleteFoodMenu(id);
    }
}
