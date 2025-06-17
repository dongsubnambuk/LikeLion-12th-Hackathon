package com.demo.nimn.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSummaryDTO {
    private Long id;
    private Long foodMenuId;
    private String foodMenuName;
    private String foodMenuImage;
    private Double averageRating;
    private Long totalReviews;
    private List<ReviewDTO> reviews;
}