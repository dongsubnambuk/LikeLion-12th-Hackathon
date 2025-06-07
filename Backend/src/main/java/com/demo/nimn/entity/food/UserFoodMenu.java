package com.demo.nimn.entity.food;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFoodMenu {
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
