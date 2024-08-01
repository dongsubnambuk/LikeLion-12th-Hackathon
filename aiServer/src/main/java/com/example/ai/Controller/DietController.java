package com.example.ai.Controller;

import com.example.ai.DTO.FoodMenuDTO;
import com.example.ai.Service.DietService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AI SERVER API", description = "AI를 사용한 식단 및 이미지 자동 생성")
@RestController
@RequestMapping(value = "/api/aiDiet")
public class DietController {
    private final DietService dietService;

    @Autowired
    public DietController(DietService dietService) {
        this.dietService = dietService;
    }

    @Operation(summary = "식단 생성(CreateDiet)", description = "가격을 입력하면 가격에 맞는 영양 있는 식단 생성")
    @PostMapping(value = "/newDiet")
    private FoodMenuDTO createDiet(@RequestParam(name = "price") String price) {
        return dietService.createDiet(price);
    }
}