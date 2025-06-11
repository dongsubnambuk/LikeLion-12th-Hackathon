package com.demo.nimn.entity.notification;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "t_user_payment", indexes = {
        @Index(name = "notification_processed", columnList = "processed, date")
})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExternalPaymentInfo {
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
    public ExternalPaymentInfo(String email, boolean processed, LocalDate date){
        this.email = email;
        this.processed = processed;
        this.date = date;
    }
}
