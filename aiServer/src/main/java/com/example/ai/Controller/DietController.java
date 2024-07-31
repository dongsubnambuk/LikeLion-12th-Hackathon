package com.example.ai.Controller;

import com.example.ai.DTO.FoodMenuDTO;
import com.example.ai.Service.DietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/aiDiet")
public class DietController {
    private final DietService dietService;

    @Autowired
    public DietController(DietService dietService) {
        this.dietService = dietService;
    }

    @PostMapping(value = "/newDiet")
    private FoodMenuDTO createDiet(@RequestParam(name = "price") String price) {
        return dietService.createDiet(price);
    }
}
