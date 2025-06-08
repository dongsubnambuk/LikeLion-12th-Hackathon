package com.demo.nimn.entity.notification;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reviewId;
    private String foodImage;
    private String foodName;
    private Long likes;
    private Long disLikes;

    @ElementCollection
    private List<String> comments;
}
