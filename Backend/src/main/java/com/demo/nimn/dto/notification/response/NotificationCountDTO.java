package com.demo.nimn.dto.notification.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "하루 식단 리뷰 정보")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationCountDTO {

    @Schema(description = "사용자 Email", example = "test@example.com", accessMode = Schema.AccessMode.READ_ONLY)
    private String userEmail;
    @Schema(description = "안읽은 알림 갯수", example = "3", accessMode = Schema.AccessMode.READ_ONLY)
    private int count;
}
