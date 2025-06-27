package com.demo.nimn.entity.payment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_payment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {
    @Id
    private String id;

    @Column(updatable = false)
    private String uid;

    @Column(updatable = false)
    private String purchaser;

    @Column(updatable = false)
    private Long totalPrice;

    @Column(updatable = false)
    private Long weeklyDietId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}