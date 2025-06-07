package com.demo.nimn.entity.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentEntity {
    @Id
    private String paymentId;
    private String paymentUid;
    private String purchaser;
    private Long totalPrice;
    private Long weeklyId;
    private LocalDateTime dateTime;

    public void updateDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}