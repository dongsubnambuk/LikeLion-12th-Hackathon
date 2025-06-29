package com.demo.nimn.controller.diet;

import com.demo.nimn.dto.diet.Response.DailyDietDTO;
import com.demo.nimn.dto.diet.Request.DailyRequestDTO;
import com.demo.nimn.service.diet.DietService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name="하루 식단 API", description = "유저 Daily 식단 API")
@RestController
@RequestMapping("/diet/daily")
public class DailyDietController {

    private final DietService dietService;

    @Autowired
    public DailyDietController(DietService dietService) {
        this.dietService = dietService;
    }

    // email과 date로 하루 식단 조회?
    @Operation(summary = "유저 하루 식단 조회", description = "Email과 Date로 유저의 하루단위 식단 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @PostMapping("/read")
    public ResponseEntity<List<DailyDietDTO>> getDailyDietsByUserEmailAndDate(@RequestBody DailyRequestDTO dailyRequestDTO) {
        List<DailyDietDTO> dailyDiets = dietService.getByUserEmailAndDate(dailyRequestDTO.getUserEmail(), dailyRequestDTO.getDate());
        return ResponseEntity.ok(dailyDiets);
    }

    // date에 해당되는 식단 조회
    @Operation(summary = "날짜별 식단 조회", description = "Date에 해당되는 유저들의 식단 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
    @GetMapping("/read/{date}")
    public ResponseEntity<List<DailyDietDTO>> getDailyDietsByDate(@PathVariable("date") LocalDate date) {
        List<DailyDietDTO> dailyDiets = dietService.getByDate(date);
        return ResponseEntity.ok(dailyDiets);
    }
}