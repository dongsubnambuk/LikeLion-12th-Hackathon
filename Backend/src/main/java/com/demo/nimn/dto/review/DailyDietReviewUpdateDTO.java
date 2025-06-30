package com.demo.nimn.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "하루 식단 리뷰 수정 요청")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyDietReviewUpdateDTO {

    @Schema(description = "수정할 리뷰 목록", required = true)
    private List<ReviewDTO> reviews;
}
