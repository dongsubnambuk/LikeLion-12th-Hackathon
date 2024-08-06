package com.example.foodserver.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long weeklyId;
    @Column
    private String userEmail;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<DailyDietEntity> dailyDiets;
}