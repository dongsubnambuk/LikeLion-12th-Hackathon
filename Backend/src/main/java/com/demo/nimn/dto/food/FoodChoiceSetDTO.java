package com.demo.nimn.dto.food;

import com.demo.nimn.enums.FoodTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "식단 선택지 정보")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodChoiceSetDTO {
    @Schema(description = "식사 시간 타입", example = "BREAKFAST", accessMode = Schema.AccessMode.READ_ONLY)
    private FoodTime foodTime;
    @Schema(description = "식단 종류", accessMode = Schema.AccessMode.READ_ONLY)
    private List<FoodDTO> foods;
}
