package com.example.foodserver.Controller;

import com.example.foodserver.DTO.DailyDietDTO;
import com.example.foodserver.Service.DailyDietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dailyDiets")
public class DailyDietController {

    private final DailyDietService dailyDietService;

    @Autowired
    public DailyDietController(DailyDietService dailyDietService) {
        this.dailyDietService = dailyDietService;
    }

    @PostMapping("/create")
    public ResponseEntity<DailyDietDTO> createDailyDiet(@RequestBody DailyDietDTO dailyDietDTO) {
        DailyDietDTO createdDiet = dailyDietService.create(dailyDietDTO);
        return ResponseEntity.ok(createdDiet);
    }

    @GetMapping("/read/{dailyDietId}")
    public ResponseEntity<DailyDietDTO> getDailyDietById(@PathVariable Long dailyDietId) {
        return dailyDietService.getDailyDietById(dailyDietId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/read/all")
    public ResponseEntity<List<DailyDietDTO>> getAllDailyDiets() {
        List<DailyDietDTO> dailyDiets = dailyDietService.getAll();
        return ResponseEntity.ok(dailyDiets);
    }

    @GetMapping("/read/user/{userId}")
    public ResponseEntity<List<DailyDietDTO>> getDailyDietsByUserId(@PathVariable Long userId) {
        List<DailyDietDTO> dailyDiets = dailyDietService.getByUserId(userId);
        return ResponseEntity.ok(dailyDiets);
    }

    @GetMapping("/read/day/{dayOfWeek}")
    public ResponseEntity<List<DailyDietDTO>> getDailyDietsByDayOfWeek(@PathVariable String dayOfWeek) {
        List<DailyDietDTO> dailyDiets = dailyDietService.getByDayOfWeek(dayOfWeek);
        return ResponseEntity.ok(dailyDiets);
    }

    @PutMapping("/update/{dailyDietId}")
    public ResponseEntity<DailyDietDTO> updateDailyDiet(@PathVariable Long dailyDietId,
                                                        @RequestBody DailyDietDTO dailyDietDTO) {
        DailyDietDTO updatedDiet = dailyDietService.update(dailyDietId, dailyDietDTO);
        return ResponseEntity.ok(updatedDiet);
    }

    @DeleteMapping("/delete/{dailyDietId}")
    public ResponseEntity<Void> deleteDailyDiet(@PathVariable Long dailyDietId) {
        dailyDietService.delete(dailyDietId);
        return ResponseEntity.noContent().build();
    }
}
