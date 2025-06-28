package com.demo.nimn.dto.notification;

import com.demo.nimn.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "알림 정보")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationDTO {
    @Schema(description = "알림 ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long notificationId;
    @Schema(description = "알림 내용", example = "오늘의 리뷰 부탁드립니다.", accessMode = Schema.AccessMode.READ_ONLY)
    private String content;
    @Schema(description = "알림 타입", example = "REVIEW", accessMode = Schema.AccessMode.READ_ONLY)
    private NotificationType type;
    @Schema(description = "알림 발송 시간", example = "2025-06-29", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime sendTime;
    @Schema(description = "알림 리뷰 ID", example = "2", accessMode = Schema.AccessMode.READ_ONLY)
    private Long dailyReviewId;
    @Schema(description = "알림 확인 여부", example = "false", accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean check;
    @Schema(description = "안읽은 알림 갯수", example = "3", accessMode = Schema.AccessMode.READ_ONLY)
    private int checksum = 0;
}
