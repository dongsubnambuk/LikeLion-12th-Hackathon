package com.example.foodserver.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenuDTO {
    Long id;
    String name;
    String image;
    String price;
    String main1;
    String main2;
    String side1;
    String side2;
    String side3;
    String calories;
    String carbohydrate;
    String protein;
    String fat;
    String sugar;
    String sodium;
}