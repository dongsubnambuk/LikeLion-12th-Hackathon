package com.demo.nimn.dto.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "결제 정보")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {

    @Schema(description = "결제 ID (PAY_ + 주문ID 형태)", example = "PAY_20241217143055ABC123")
    private String id;

    @Schema(description = "아임포트 결제 고유 번호 (imp_uid)", example = "imp_1234567890")
    private String uid;

    @Schema(description = "구매자 이메일", example = "user@example.com")
    private String purchaser;

    @Schema(description = "총 결제 금액", example = "50000", minimum = "0")
    private Long totalPrice;

    @Schema(description = "주간 식단 ID", example = "1")
    private Long weeklyDietId;

    @Schema(description = "결제 생성 시간", example = "2024-12-17T14:30:55.123456")
    private LocalDateTime createdAt;
}