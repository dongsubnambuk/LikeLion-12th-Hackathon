package com.example.ai.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    String name;
    @Column(length = 1024)
    String image;
    @Column
    String price;
    @Column
    String main1;
    @Column
    String main2;
    @Column
    String side1;
    @Column
    String side2;
    @Column
    String side3;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    NutritionFacts nutritionFacts;
}
