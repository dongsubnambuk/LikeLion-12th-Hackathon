package com.demo.nimn.entity.review;

import com.demo.nimn.entity.food.Food;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_review")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail;

    @ManyToOne
    @JoinColumn(name = "food_menu_id", nullable = false)
    private Food food;

    @DecimalMin("1.0")
    @DecimalMax("5.0")
    @Column(nullable = false)
    private Double rating;

    private String comment;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
