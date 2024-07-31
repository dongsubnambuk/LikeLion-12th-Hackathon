package com.example.ai.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
