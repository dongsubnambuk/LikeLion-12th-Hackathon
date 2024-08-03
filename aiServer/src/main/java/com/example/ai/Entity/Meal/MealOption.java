package com.example.ai.Entity.Meal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealOption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mealOptionId;
    @Column
    private String mealType; // 아침, 점심, 저녁
    @ManyToMany
    @JoinTable(
            name = "meal_option_food_menu",
            joinColumns = @JoinColumn(name = "meal_option_id"),
            inverseJoinColumns = @JoinColumn(name = "food_menu_id")
    )
    private List<FoodMenu> foodMenus;
}
