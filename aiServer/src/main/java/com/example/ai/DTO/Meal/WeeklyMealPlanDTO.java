package com.example.ai.DTO.Meal;

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
public class WeeklyMealPlanDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<DailyMealPlanDTO> dailyMealPlans;
}
