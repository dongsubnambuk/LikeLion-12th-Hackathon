package com.demo.nimn.dto.notification;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ExternalPaymentNotificationDTO {
    private List<String> emails;
    boolean processed = false;
    private LocalDate date = LocalDate.now();

    @Builder
    public ExternalPaymentNotificationDTO(List<String> emails, boolean processed, LocalDate date){
        this.emails = emails;
        this.processed = processed;
        this.date = date;
    }
}
