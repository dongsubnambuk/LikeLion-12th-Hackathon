package com.demo.nimn.dto.food;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyFoodPlanDTO {
    private LocalDate day;
    private List<FoodChoiceSetDTO> foodChoiceSets;
}
