package com.demo.nimn.dto.payment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "결제 생성 요청 정보")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {

    @Schema(description = "아임포트 결제 고유 번호 (imp_uid)", example = "imp_1234567890", required = true)
    private String paymentUid;

    @Schema(description = "주문 고유 번호", example = "20241217143055ABC123", required = true)
    private String orderId;
}