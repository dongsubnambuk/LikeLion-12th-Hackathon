package com.demo.nimn.dto.notification;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PaymentNotificationDTO {
    private Long id;
    private String email;
    private String notificationContent;
    private LocalDateTime notificationTime;

    @Builder
    public PaymentNotificationDTO(Long id,
                                  String email,
                                  String notificationContent,
                                  LocalDateTime notificationTime){
        this.id = id;
        this.email = email;
        this.notificationContent = notificationContent;
        this.notificationTime = notificationTime;
    }
}