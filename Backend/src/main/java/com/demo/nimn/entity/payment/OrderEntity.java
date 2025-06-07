package com.demo.nimn.entity.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.time.LocalDateTime;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEntity {
    @Id
    private String orderId;
    @Column
    private String purchaser;
    @Column
    private Long totalPrice;
    @Column
    private Long weeklyId;
    @Column
    private LocalDateTime dateTime;

    public void updateDateTime(LocalDateTime dateTime){
        this.dateTime = dateTime;
    }

    private static String generateRandomOrderId() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(20);
        for (int i = 0; i < 20; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public static OrderEntity.OrderEntityBuilder builder() {
        return new OrderEntity.OrderEntityBuilder() {
            @Override
            public OrderEntity build() {
                if (super.orderId == null) {
                    super.orderId = generateRandomOrderId();
                }
                return super.build();
            }
        };
    }
}
