package com.example.foodserver.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "daily_diet")
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor

public class DailyDietEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dayOfWeek; // Example: "Monday", "Tuesday", etc.

    @Column(nullable = false)
    private String mealTime; // Example: "아침", "점심", "저녁"

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private Long foodMenuId;
}
