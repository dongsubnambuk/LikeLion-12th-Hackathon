package com.demo.nimn.entity.food;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "t_weekly_meal_plan")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeeklyFoodPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    private List<DailyFoodPlan> dailyFoodPlans;

    private LocalDate startDate;

    private LocalDate endDate;
}
