package com.demo.nimn.entity.diet;

import com.demo.nimn.entity.food.Food;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_food_selection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodSelection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    @ManyToOne
    @JoinColumn(name = "food_menu_id")
    private Food food;

    private String mealTime;

    private int count;
}