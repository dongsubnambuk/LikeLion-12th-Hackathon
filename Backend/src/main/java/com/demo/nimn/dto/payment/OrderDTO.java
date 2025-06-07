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
public class OrderDTO {
    private String orderId;
    private String purchaser;
    private Long totalPrice;
    private Long weeklyId;
    private LocalDateTime dateTime;
}
