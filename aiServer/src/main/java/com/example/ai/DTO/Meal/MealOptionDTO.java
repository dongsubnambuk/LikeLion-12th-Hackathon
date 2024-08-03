package com.example.ai.DTO.Meal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealOptionDTO {
    private String mealType;
    private List<FoodMenuDTO> foodMenus;
}
