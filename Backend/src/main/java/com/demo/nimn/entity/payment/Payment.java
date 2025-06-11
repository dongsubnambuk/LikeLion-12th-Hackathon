package com.demo.nimn.entity.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_payment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {
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