package com.example.ai.Entity.Review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyReview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long dailyReviewId;
    @Column
    private String userEmail;
    @Column
    private LocalDate reviewDate;
    @OneToMany
    @JoinColumn
    private List<Review> reviews;
}