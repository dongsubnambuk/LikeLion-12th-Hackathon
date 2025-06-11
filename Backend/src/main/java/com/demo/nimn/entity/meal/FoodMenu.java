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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long foodMenuId;
    @Column
    private String name;
    @Column
    private String image;
    @Column
    private String price;
    @Column
    private String main1;
    @Column
    private String main2;
    @Column
    private String side1;
    @Column
    private String side2;
    @Column
    private String side3;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private NutritionFact nutritionFact;
}
