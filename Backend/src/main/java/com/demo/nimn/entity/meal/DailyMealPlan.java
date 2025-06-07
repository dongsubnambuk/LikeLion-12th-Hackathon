package com.demo.nimn.entity.meal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyMealPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long dailyMealPlanId;

    @Column
    private LocalDate day;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "daily_meal_plan_meal_option",
            joinColumns = @JoinColumn(name = "daily_meal_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_option_id")
    )
    private List<MealOption> mealOptions;
}