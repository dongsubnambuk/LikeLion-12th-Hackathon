package com.example.foodserver.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "food_menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodMenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private String price;

    @Column
    private String calories;

    @Column
    private String image;

    @Column
    private String carbohydrate;

    @Column
    private String protein;

    @Column
    private String fat;

    @Column
    private String sugar;

    @Column
    private String sodium;
}
