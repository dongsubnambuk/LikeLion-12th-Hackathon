package com.demo.nimn.dto.food;

import com.demo.nimn.enums.FoodTime;
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
    private FoodTime foodTime;
    private List<FoodDTO> foods;
}
