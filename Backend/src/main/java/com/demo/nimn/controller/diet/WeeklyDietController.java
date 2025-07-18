package com.demo.nimn.controller.diet;

import com.demo.nimn.dto.diet.WeeklyDietDTO;
import com.demo.nimn.dto.diet.CreateWeeklyDietDTO;
import com.demo.nimn.service.diet.DietService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="한 주 식단 API", description = "유저 한 주 식단 관리")
@RestController
@RequestMapping("/diet/weekly")
public class WeeklyDietController {

    private final DietService dietService;

    @Autowired
    public WeeklyDietController(DietService dietService) {
        this.dietService = dietService;
    }

    @Operation(summary = "유저 일주일 식단 생성", description = "유저의 일주일 식단 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content()),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content()),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content())
    })
    @PostMapping("/create") // 유저 일주일 식단 생성
    public ResponseEntity<WeeklyDietDTO> createWeeklyDiet(@RequestBody CreateWeeklyDietDTO weeklyDietDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(dietService.createWeeklyDiet(weeklyDietDTO));
    }

    @Operation(summary = "유저의 일주일 식단 조회", description = "유저의 특정 일주일 식단 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패", content = @Content()),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content()),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content())
    })
    @GetMapping("/read/{userEmail}") // 유저 특정 일주일 식단 조회
    public ResponseEntity<WeeklyDietDTO> getWeeklyByUserEmail(@PathVariable("userEmail") String userEmail) {
        return ResponseEntity.status(HttpStatus.OK).body(dietService.getWeeklyDietByUserEmail(userEmail));
    }
}