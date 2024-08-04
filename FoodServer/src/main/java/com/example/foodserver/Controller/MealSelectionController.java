package com.example.foodserver.Controller;

import com.example.foodserver.DTO.MealSelectionDTO;
import com.example.foodserver.Service.MealSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userMeal/selections")
public class MealSelectionController {

    private final MealSelectionService mealSelectionService;

    @Autowired
    public MealSelectionController(MealSelectionService mealSelectionService) {
        this.mealSelectionService = mealSelectionService;
    }

    @GetMapping("read/{mealSelectionId}")
    public ResponseEntity<MealSelectionDTO> getMealSelectionById(@PathVariable Long mealSelectionId) {
        MealSelectionDTO mealSelectionDTO = mealSelectionService.getById(mealSelectionId);
        return ResponseEntity.ok(mealSelectionDTO);
    }
}
