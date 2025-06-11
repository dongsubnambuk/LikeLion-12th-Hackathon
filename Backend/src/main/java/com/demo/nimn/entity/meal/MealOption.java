package com.demo.nimn.entity.meal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "t_meal_option")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mealType;

    @ManyToMany(mappedBy = "mealOptions")
    private List<DailyMealPlan> dailyMealPlans;

    @ManyToMany
    @JoinTable(
            name = "t_meal_option_food_menu",
            joinColumns = @JoinColumn(name = "meal_option_id"),
            inverseJoinColumns = @JoinColumn(name = "food_menu_id", referencedColumnName = "id")
    )
    private List<FoodMenu> foodMenus;
}