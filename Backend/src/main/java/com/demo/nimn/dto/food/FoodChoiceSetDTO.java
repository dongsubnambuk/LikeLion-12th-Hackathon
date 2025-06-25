package com.demo.nimn.dto.food;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodChoiceSetDTO {
    private String foodType;
    private List<FoodDTO> foods;
}
