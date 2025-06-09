package com.demo.nimn.dto.notification;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SurveyNotificationDTO {
    private String email;
    private String notificationContent;
    private LocalDate reviewDate;
    private Long dailyReviewId;
    private LocalDateTime notificationTime;
    private List<ReviewDTO> reviews;
    // 롬복을 사용하므로 getter, setter, builder 등이 자동으로 생성됩니다
}


