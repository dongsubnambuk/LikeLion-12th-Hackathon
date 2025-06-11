package com.demo.nimn.entity.food;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "t_weekly_diet")
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class WeeklyDiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private LocalDate startDate;

    private LocalDate endDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<DailyDiet> dailyDiets;
}