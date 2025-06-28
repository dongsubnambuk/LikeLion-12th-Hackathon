package com.demo.nimn.dto.food;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Schema(description = "음식 정보")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDTO {
    @Schema(description = "음식 ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    Long id;
    @Schema(description = "식단 이름", example = "고들빼기 스테이크와 미소국 세트", accessMode = Schema.AccessMode.READ_ONLY)
    String name;
    @Schema(description = "이미지 링크", example = "http://test.com/test-image", accessMode = Schema.AccessMode.READ_ONLY)
    String image;
    @Schema(description = "식단 가격", example = "10000", accessMode = Schema.AccessMode.READ_ONLY)
    Long price;
    @Schema(description = "1 메인 음식 이름", example = "고들빼기 스테이크 (150g)", accessMode = Schema.AccessMode.READ_ONLY)
    String main1;
    @Schema(description = "2 메인 음식 이름", example = "미소국 (200ml)", accessMode = Schema.AccessMode.READ_ONLY)
    String main2;
    @Schema(description = "1 사이드 음식 이름", example = "무쌈 (50g)", accessMode = Schema.AccessMode.READ_ONLY)
    String side1;
    @Schema(description = "2 사이드 음식 이름", example = "시금치 나물 (50g)", accessMode = Schema.AccessMode.READ_ONLY)
    String side2;
    @Schema(description = "3 사이드 음식 이름", example = "무말랭이 (30g)", accessMode = Schema.AccessMode.READ_ONLY)
    String side3;
    @Schema(description = "칼로리 수치", example = "800kcal", accessMode = Schema.AccessMode.READ_ONLY)
    String calories;
    @Schema(description = "탄수화물 함량", example = "85g", accessMode = Schema.AccessMode.READ_ONLY)
    String carbohydrate;
    @Schema(description = "단백질 함량", example = "45g", accessMode = Schema.AccessMode.READ_ONLY)
    String protein;
    @Schema(description = "지방 함량", example = "23g", accessMode = Schema.AccessMode.READ_ONLY)
    String fat;
    @Schema(description = "당 함량", example = "9g", accessMode = Schema.AccessMode.READ_ONLY)
    String sugar;
    @Schema(description = "염분 함량", example = "900mg", accessMode = Schema.AccessMode.READ_ONLY)
    String sodium;
}
