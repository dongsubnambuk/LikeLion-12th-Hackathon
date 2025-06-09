package com.demo.nimn.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyReviewDTO {
    private Long dailyReviewId;
    private String userEmail;
    private LocalDate reviewDate;
    private List<ReviewDTO> reviews;
}

