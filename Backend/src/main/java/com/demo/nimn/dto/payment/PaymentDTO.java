package com.demo.nimn.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDTO {
    private String id;
    private String uid;
    private String purchaser;
    private Long totalPrice;
    private Long weeklyDietId;
    private LocalDateTime createdAt;
}
