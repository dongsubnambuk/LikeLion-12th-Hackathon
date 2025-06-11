package com.demo.nimn.entity.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;
import java.time.LocalDateTime;


@Entity
@Table(name="t_order")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    @Id
    private String orderId;

    private String purchaser;

    private Long totalPrice;

    private Long weeklyId;

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

    public static Order.OrderBuilder builder() {
        return new Order.OrderBuilder() {
            @Override
            public Order build() {
                if (super.orderId == null) {
                    super.orderId = generateRandomOrderId();
                }
                return super.build();
            }
        };
    }
}
