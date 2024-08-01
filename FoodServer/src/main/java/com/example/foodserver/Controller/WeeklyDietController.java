package com.example.foodserver.Controller;

import com.example.foodserver.DTO.WeeklyDietDTO;
import com.example.foodserver.Service.WeeklyDietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weekly")
public class WeeklyDietController {

    private final WeeklyDietService weeklyDietService;

    @Autowired
    public WeeklyDietController(WeeklyDietService weeklyDietService) {
        this.weeklyDietService = weeklyDietService;
    }

    @PostMapping("/create")
    public WeeklyDietDTO createWeeklyDiet(@RequestBody WeeklyDietDTO weeklyDietDTO) {
        return weeklyDietService.createWeeklyDiet(weeklyDietDTO);
    }

    @GetMapping
    public List<WeeklyDietDTO> getAllWeeklyDiets() {
        return weeklyDietService.getAllWeeklyDiets();
    }

    @GetMapping("/{id}")
    public WeeklyDietDTO getWeeklyDietById(@PathVariable Long id) {
        return weeklyDietService.getWeeklyDietById(id)
                .orElseThrow(() -> new RuntimeException("WeeklyDiet not found with id " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteWeeklyDiet(@PathVariable Long id) {
        weeklyDietService.deleteWeeklyDiet(id);
    }
}
