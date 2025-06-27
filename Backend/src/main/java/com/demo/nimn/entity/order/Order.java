package com.demo.nimn.entity.order;

import com.demo.nimn.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "t_order")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    @Id
    @Column(length = 20)
    private String orderId;

    @Column(nullable = false)
    private String purchaser;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private Long weeklyDietId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDING;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    private static String generateRandomOrderId() {
        // 시간 기반 (14자리) + 랜덤 (6자리)
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder randomPart = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            randomPart.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return timestamp + randomPart;
    }

    @PrePersist
    private void prePersist() {
        if (this.orderId == null) {
            this.orderId = generateRandomOrderId();
        }
    }

    public void pay() {
        this.status = OrderStatus.PAID;
    }

    public void cancel() {
        if (this.status == OrderStatus.PAID) {
            throw new IllegalStateException("이미 결제된 주문은 취소할 수 없습니다.");
        }
        this.status = OrderStatus.CANCELLED;
    }
}
