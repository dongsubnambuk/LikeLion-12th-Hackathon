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
    private Long dailyId;

    @Column(nullable = false)
    private String dayOfWeek;

    @Column(nullable = false)
    private Long foodMenuId;
}
