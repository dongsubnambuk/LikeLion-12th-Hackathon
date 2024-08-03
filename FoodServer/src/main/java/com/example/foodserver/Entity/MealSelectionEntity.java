package com.example.foodserver.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "meal_selection")
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor

public class MealSelectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long dailyDietId;

    @Column(nullable = false)
    private Long foodMenuId;

    @Column(nullable = false)
    private String mealTime; // Example: "아침", "점심", "저녁"

    @Column(nullable = false)
    private int count;
}
