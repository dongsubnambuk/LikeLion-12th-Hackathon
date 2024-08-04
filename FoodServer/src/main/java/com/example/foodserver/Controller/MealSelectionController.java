package com.example.foodserver.Controller;

import com.example.foodserver.DTO.DailyDietDTO;
import com.example.foodserver.DTO.MealSelectionDTO;
import com.example.foodserver.Service.MealSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/create")
    public ResponseEntity<MealSelectionDTO> createMealSelection(@RequestBody MealSelectionDTO mealSelectionDTO) {
        MealSelectionDTO createdSelection = mealSelectionService.create(mealSelectionDTO);
        return ResponseEntity.ok(createdSelection);
    }

    @PostMapping("/weekly/create")
    public ResponseEntity<List<DailyDietDTO>> createWeeklyDiet(@RequestParam Long userId,
                                                               @RequestBody List<DailyDietDTO> dailyDiets) {
        List<DailyDietDTO> createdDiets = mealSelectionService.createWeeklyDiet(userId, dailyDiets);
        return ResponseEntity.ok(createdDiets);
    }

    @GetMapping("read/all")
    public ResponseEntity<List<MealSelectionDTO>> getAllMealSelections() {
        List<MealSelectionDTO> mealSelections = mealSelectionService.getAll();
        return ResponseEntity.ok(mealSelections);
    }

    @GetMapping("read/{mealSelectionId}")
    public ResponseEntity<MealSelectionDTO> getMealSelectionById(@PathVariable Long mealSelectionId) {
        return mealSelectionService.getById(mealSelectionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("read/user/{userId}")
    public ResponseEntity<List<MealSelectionDTO>> getMealSelectionsByUserId(@PathVariable Long userId) {
        List<MealSelectionDTO> mealSelections = mealSelectionService.getByUserId(userId);
        return ResponseEntity.ok(mealSelections);
    }

    @PutMapping("update/{mealSelectionId}")
    public ResponseEntity<MealSelectionDTO> updateMealSelection(@PathVariable Long mealSelectionId,
                                                                @RequestBody MealSelectionDTO mealSelectionDTO) {
        MealSelectionDTO updatedSelection = mealSelectionService.update(mealSelectionId, mealSelectionDTO);
        return ResponseEntity.ok(updatedSelection);
    }

    @DeleteMapping("delete/{mealSelectionId}")
    public ResponseEntity<Void> deleteMealSelection(@PathVariable Long mealSelectionId) {
        mealSelectionService.delete(mealSelectionId);
        return ResponseEntity.noContent().build();
    }
}
