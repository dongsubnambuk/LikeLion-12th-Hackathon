package com.demo.nimn.controller.diet;

import com.demo.nimn.dto.diet.FoodSelectionDTO;
import com.demo.nimn.service.diet.FoodSelectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="선택 식단 API", description = "하루 식단 중 한끼에 대한 관리")
@RestController
@RequestMapping("/diet/selections")
public class FoodSelectionController {

    private final FoodSelectionService foodSelectionService;

    @Autowired
    public FoodSelectionController(FoodSelectionService foodSelectionService) {
        this.foodSelectionService = foodSelectionService;
    }
    // 선택한 음식 조회
    @Operation(summary = "선택한 음식 조회", description = "유저의 선택한 음식의 상세정보 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content()),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content()),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content())
    })
    @GetMapping("read/{mealSelectionId}")
    public ResponseEntity<FoodSelectionDTO> getMealSelectionById(@PathVariable Long mealSelectionId) {
        FoodSelectionDTO foodSelectionDTO = foodSelectionService.getById(mealSelectionId);
        return ResponseEntity.ok(foodSelectionDTO);
    }
}
