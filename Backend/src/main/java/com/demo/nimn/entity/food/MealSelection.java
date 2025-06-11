package com.demo.nimn.entity.food;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_meal_selection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealSelection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private Long foodMenuId;

    private String mealTime;

    private int count;
}