package com.demo.nimn.dto.order;

import com.demo.nimn.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "주문 정보")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {

    @Schema(description = "주문 ID (자동 생성)", example = "20241217143055ABC123")
    private String orderId;

    @Schema(description = "주문자 이메일", example = "user@example.com", required = true)
    private String purchaser;

    @Schema(description = "총 주문 금액", example = "50000")
    private Long totalPrice;

    @Schema(description = "사용자가 선택한 주간 식단 ID", example = "1", required = true)
    private Long weeklyDietId;

    @Schema(description = "주문 상태", example = "PENDING")
    private OrderStatus status;

    @Schema(description = "주문 생성 시간", example = "2024-12-17T14:30:55")
    private LocalDateTime createdAt;
}