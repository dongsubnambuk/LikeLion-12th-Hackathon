package com.example.foodserver.DTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DailyDietDTO {
    private Long dailyDietId;
    private LocalDate date;
    private List<MealSelectionDTO> mealSelections;
    private String userEmail;
}