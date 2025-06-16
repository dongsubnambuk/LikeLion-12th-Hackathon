package com.demo.nimn.controller.diet;

import com.demo.nimn.dto.diet.Response.FoodSelectionDTO;
import com.demo.nimn.service.diet.FoodSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userMeal/selections")
public class FoodSelectionController {

    private final FoodSelectionService foodSelectionService;

    @Autowired
    public FoodSelectionController(FoodSelectionService foodSelectionService) {
        this.foodSelectionService = foodSelectionService;
    }

    @GetMapping("read/{mealSelectionId}")
    public ResponseEntity<FoodSelectionDTO> getMealSelectionById(@PathVariable Long mealSelectionId) {
        FoodSelectionDTO foodSelectionDTO = foodSelectionService.getById(mealSelectionId);
        return ResponseEntity.ok(foodSelectionDTO);
    }
}
