package com.example.paymentserver.DTO;

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
    private Long orderId;
    private String purchaser;
    private Long totalPrice;
    private Long weeklyId;
    private LocalDateTime dateTime;
}
