package com.demo.nimn.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "하루 식단 리뷰 정보")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyDietReviewDTO {

    @Schema(description = "하루 식단 리뷰 ID", example = "1")
    private Long id;

    @Schema(description = "사용자 이메일", example = "user@example.com")
    private String userEmail;

    @Schema(description = "리뷰 대상 날짜", example = "2024-12-17")
    private LocalDate reviewDate;

    @Schema(description = "해당 날짜의 모든 음식에 대한 리뷰 목록", type = "array")
    private List<ReviewDTO> reviews;
}