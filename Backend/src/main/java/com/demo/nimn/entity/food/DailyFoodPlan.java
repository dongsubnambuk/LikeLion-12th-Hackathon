package com.demo.nimn.entity.food;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "t_daily_food_plan")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyFoodPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate day;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "t_daily_food_plan_food_choice_set",
            joinColumns = @JoinColumn(name = "daily_food_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "food_choice_set_id", referencedColumnName = "id")
    )
    private List<FoodChoiceSet> foodChoiceSets;
}