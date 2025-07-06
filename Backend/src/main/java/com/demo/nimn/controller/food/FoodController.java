package com.demo.nimn.controller.food;

import com.demo.nimn.dto.food.FoodDTO;
import com.demo.nimn.dto.food.WeeklyFoodPlanDTO;
import com.demo.nimn.service.food.FoodService;
import com.demo.nimn.service.food.WeeklyFoodPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "음식 API", description = "식단에 포함되는 음식 관리")
@RestController
@RequestMapping(value = "/foods")
public class FoodController {
    private final FoodService foodService;
    private final WeeklyFoodPlanService weeklyFoodPlanService;

    @Autowired
    public FoodController(FoodService foodService, WeeklyFoodPlanService weeklyFoodPlanService) {
        this.foodService = foodService;
        this.weeklyFoodPlanService = weeklyFoodPlanService;
    }

    @Operation(summary = "음식 생성", description = "입력된 가격을 기준으로, 영양을 고려한 새로운 음식 데이터를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "음식 생성 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FoodDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "입력된 가격 형식이 잘못되었거나 유효하지 않은 값입니다.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content
            )
    })
    @PostMapping(value = "/food")
    public ResponseEntity<FoodDTO> createFood(@Parameter(description = "가격", schema = @Schema(defaultValue = "0"), example = "10000")
                                                @RequestParam(name = "price", defaultValue = "0") Long price) {
        FoodDTO foodDTO = foodService.createFood(price);
        return ResponseEntity.created(URI.create("/foods/food/" + foodDTO.getId())).body(foodDTO);
    }

    @Operation(summary = "한 주 식단 선택지 생성", description = "다음 주 월요일부터 일주일간의 식단 선택지를 생성합니다. 이미 생성된 경우 기존 데이터를 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "주간 식단 생성 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WeeklyFoodPlanDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "식단 생성 중 서버 내부 오류 발생",
                    content = @Content
            )
    })
    @PostMapping(value = "/plans/weekly")
    public ResponseEntity<WeeklyFoodPlanDTO> createWeeklyFoodPlan() {
        return ResponseEntity.created(URI.create("/foods/plans/weekly")).body(weeklyFoodPlanService.createWeeklyPlan());
    }

    @Operation(summary = "한 주 식단 선택지 조회", description = "다음 주 월요일 기준의 주간 식단 선택지를 조회합니다. 식단이 없을 경우 자동으로 생성하여 반환합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "주간 식단 조회 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = WeeklyFoodPlanDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "식단 조회 중 서버 내부 오류 발생",
                    content = @Content
            )
    })
    @GetMapping(value = "/plans/weekly")
    public ResponseEntity<WeeklyFoodPlanDTO> readWeeklyFoodPlan() {
        return ResponseEntity.ok(weeklyFoodPlanService.readWeeklyFoodPlan());
    }

    @Operation(summary = "모든 음식 목록 조회", description = "현재 저장된 모든 음식 데이터를 리스트로 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "음식 목록 조회 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FoodDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "데이터 조회 중 서버 오류 발생",
                    content = @Content
            )
    })
    @GetMapping(value = "/plans")
    public ResponseEntity<List<FoodDTO>> readAllFood() {
        return ResponseEntity.ok(foodService.readAll());
    }

    @Operation(summary = "특정 음식 조회", description = "음식 ID를 기반으로 특정 음식의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "음식 조회 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FoodDTO.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류 발생",
                    content = @Content
            )
    })
    @GetMapping(value = "/food/{foodId}")
    public ResponseEntity<FoodDTO> readByFoodId(@Parameter(description = "음식 ID", example = "1")
                                                @PathVariable("foodId") Long foodId) {
        return ResponseEntity.ok(foodService.readFoodDTOByFoodId(foodId));
    }
}