package com.example.foodserver.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "meal_selection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealSelectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mealSelectionId;

    private Long userId;
    private Long foodMenuId;
    private String mealTime;
    private int count;

    @ManyToOne
    @JoinColumn(name = "daily_diet_id")
    private DailyDietEntity dailyDiet;
}
