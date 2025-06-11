package com.demo.nimn.entity.food;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_user_food_menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFoodMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String price;

    private String calories;

    private String image;

    private String carbohydrate;

    private String protein;

    private String fat;

    private String sugar;

    private String sodium;
}
