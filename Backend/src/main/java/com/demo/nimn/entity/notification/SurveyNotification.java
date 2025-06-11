package com.demo.nimn.entity.notification;

import com.demo.nimn.entity.review.Review;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_survey_notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SurveyNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String notificationContent;

    private LocalDateTime notificationTime;

    private Long dailyReviewId;

    private LocalDate reviewDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "survey_notification_id")
    private List<Review> reviews;
}
