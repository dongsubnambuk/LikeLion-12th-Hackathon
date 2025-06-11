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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long dailyDietId;
    @Column
    private LocalDate date;
    @Column
    private String userEmail;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<MealSelection> mealSelections;
}