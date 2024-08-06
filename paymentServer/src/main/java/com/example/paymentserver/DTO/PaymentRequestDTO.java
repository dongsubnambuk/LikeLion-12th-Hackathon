package com.example.paymentserver.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {
    private String paymentUid; // 결제 고유 번호
    private String orderId; // 주문 고유 번호
}