package com.demo.nimn.dto.food;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FoodMenuDTO {
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
