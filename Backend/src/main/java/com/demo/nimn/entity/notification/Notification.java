package com.demo.nimn.entity.notification;

import com.demo.nimn.dto.notification.NotificationDTO;
import com.demo.nimn.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_notification")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Column(nullable = false)
    private LocalDateTime sendTime;

    private Long dailyReviewId;

    @Column(name = "is_checked", nullable = false)
    private Boolean check = false;

    public NotificationDTO toNotificationDTO(int checksum) {
        return NotificationDTO.builder()
                .notificationId(this.notificationId)
                .content(this.content)
                .type(this.type)
                .sendTime(this.sendTime)
                .dailyReviewId(this.dailyReviewId)
                .check(this.check)
                .checksum(checksum)
                .build();
    }
}
