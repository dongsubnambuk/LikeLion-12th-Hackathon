package com.demo.nimn.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "주문 생성 요청 정보")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {
    @Schema(description = "주문자 이메일", example = "user@example.com", required = true)
    private String purchaser;

    @Schema(description = "사용자가 선택한 주간 식단 ID", example = "1", required = true)
    private Long weeklyDietId;
}