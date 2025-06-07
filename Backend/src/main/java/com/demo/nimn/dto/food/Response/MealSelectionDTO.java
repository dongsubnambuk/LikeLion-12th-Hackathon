package com.demo.nimn.dto.food.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MealSelectionDTO {
    private Long mealSelectionId;
    private String userEmail;
    private String mealTime;
    private FoodMenuDTO foodMenu;
    private int count;
}