package com.demo.nimn.entity.notification;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_payment", indexes = {
        @Index(name = "notification_processed", columnList = "processed, date")
})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExternalPaymentInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    @Column
    private boolean processed = false;
    @Column
    private LocalDate date;

    @Builder
    public ExternalPaymentInfoEntity(String email, boolean processed, LocalDate date){
        this.email = email;
        this.processed = processed;
        this.date = date;
    }
}
