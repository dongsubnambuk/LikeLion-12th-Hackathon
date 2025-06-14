package com.demo.nimn.dto.notification;

import com.demo.nimn.enums.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationDTO {
    private Long notificationId;
    private String content;
    private NotificationType type;
    private LocalDateTime sendTime;
    private Long dailyReviewId;
    private Boolean check;
    private int checksum = 0;
}
