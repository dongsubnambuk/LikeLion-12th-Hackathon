package com.demo.nimn.dto.food;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "일일 식단 정보")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyFoodPlanDTO {
    @Schema(description = "날짜", example = "05-25", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDate day;
    @Schema(description = "식단 모음 리스트", accessMode = Schema.AccessMode.READ_ONLY)
    private List<FoodChoiceSetDTO> foodChoiceSets;
}
