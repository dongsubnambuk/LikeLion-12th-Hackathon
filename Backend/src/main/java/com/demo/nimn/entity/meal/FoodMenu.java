package com.demo.nimn.entity.meal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_food_menu")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private String price;

    private String main1;

    private String main2;

    private String side1;

    private String side2;

    private String side3;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private NutritionFact nutritionFact;
}
