package com.demo.nimn.dto.meal;

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
public class DailyMealPlanDTO {
    private LocalDate day;
    private List<MealOptionDTO> mealOptions;
}
