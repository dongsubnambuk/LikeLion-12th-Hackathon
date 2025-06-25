package com.demo.nimn.entity.food;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "t_food_choice_set")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodChoiceSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mealType;

    @ManyToMany(mappedBy = "foodChoiceSets")
    private List<DailyFoodPlan> dailyFoodPlans;

    @ManyToMany
    @JoinTable(
            name = "t_food_choice_set_food",
            joinColumns = @JoinColumn(name = "food_choice_set_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id", referencedColumnName = "id")
    )
    private List<Food> foods;
}