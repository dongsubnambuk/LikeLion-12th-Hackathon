package com.demo.nimn.entity.review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_daily_review")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private LocalDate reviewDate;

    @ManyToMany
    @JoinTable(
            name = "t_daily_review_review",
            joinColumns = @JoinColumn(name = "daily_review_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    public void addReview(Review review) {
        this.reviews.add(review);
        review.getDailyReviews().add(this);
    }
}