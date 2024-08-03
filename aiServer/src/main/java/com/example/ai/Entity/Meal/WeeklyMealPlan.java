package com.example.ai.Entity.Meal;

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
public class WeeklyMealPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long weeklyMealPlanId;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "weekly_meal_plan_id")
    private List<DailyMealPlan> dailyMealPlans;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
}
