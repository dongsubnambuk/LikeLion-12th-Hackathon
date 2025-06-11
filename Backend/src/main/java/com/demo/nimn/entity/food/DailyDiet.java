package com.demo.nimn.entity.food;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "t_daily_diet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyDiet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @Column(length = 30)
    private String userEmail;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<MealSelection> mealSelections;
}