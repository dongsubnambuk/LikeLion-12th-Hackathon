package com.demo.nimn.entity.review;

import com.demo.nimn.entity.food.Food;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "t_review_summary")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSummary {
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "food_id", nullable = false)
    @MapsId
    private Food food;

    @Builder.Default
    private Double averageRating = 0.0;

    @Builder.Default
    private Long totalReviews = 0L;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "food_id")
    private List<Review> reviews;
}