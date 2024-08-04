package com.example.foodserver.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "weekly_diet")
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor

public class WeeklyDietEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weeklyId;

    @Column
    private Long userId;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;
}
