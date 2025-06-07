package com.demo.nimn.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long reviewId;
    private String foodImage;
    private String foodName;
    private Long likes;
    private Long disLikes;
    private List<String> comment;
}