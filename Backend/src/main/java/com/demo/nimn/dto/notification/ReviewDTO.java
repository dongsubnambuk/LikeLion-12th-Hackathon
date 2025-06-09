package com.demo.nimn.dto.notification;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ReviewDTO {
    private Long reviewId;
    private String foodImage;
    private String foodName;
    private Long likes;
    private Long disLikes;
    private List<String> comments;
}
