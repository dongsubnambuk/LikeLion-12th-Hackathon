package com.demo.nimn.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "음식별 종합 리뷰 정보")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSummaryDTO {

    @Schema(description = "종합 리뷰 정보 ID", example = "1")
    private Long id;

    @Schema(description = "음식 ID", example = "1")
    private Long foodId;

    @Schema(description = "음식 이름", example = "김치찌개 정식")
    private String foodName;
    
    @Schema(description = "음식 이미지 URL", example = "/api/image/123")
    private String foodImage;

    @Schema(description = "평균 평점 (1.0 ~ 5.0)", example = "4.2", minimum = "0.0", maximum = "5.0")
    private Double averageRating;

    @Schema(description = "총 리뷰 개수", example = "15", minimum = "0")
    private Long totalReviews;

    @Schema(description = "완료된 리뷰 목록 (평점이 있는 리뷰만)", type = "array")
    private List<ReviewDTO> reviews;
}