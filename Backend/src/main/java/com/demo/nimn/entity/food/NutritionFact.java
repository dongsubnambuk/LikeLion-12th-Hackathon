package com.demo.nimn.entity.food;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_nutrition_fact")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionFact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String calories;

    private String carbohydrate;

    private String protein;

    private String fat;

    private String sugar;

    private String sodium;
}
