package com.example.foodserver.DTO;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DailyDietDTO {
    private Long dailyDietId;
    private String dayOfWeek;
    private Long foodMenuId;
    private Long userId;
    private List<MealSelectionDTO> mealSelections;

}
