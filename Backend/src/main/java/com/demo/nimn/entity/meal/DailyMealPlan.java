package com.demo.nimn.entity.meal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "t_daily_meal_plan")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyMealPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate day;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "t_daily_meal_plan_meal_option",
            joinColumns = @JoinColumn(name = "daily_meal_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_option_id", referencedColumnName = "id")
    )
    private List<MealOption> mealOptions;
}