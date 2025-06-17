package com.demo.nimn.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private String userEmail;
    private Long foodMenuId;
    private String foodMenuName;
    private String foodMenuImage;
    private Double rating;
    private String comment;
    private LocalDateTime createdAt;
}