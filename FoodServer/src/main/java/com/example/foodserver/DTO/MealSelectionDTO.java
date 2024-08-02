package com.example.foodserver.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MealSelectionDTO {
    private Long mealSelectionId;
    private Long userId;
    private Long dailyDietId;
    private Long foodMenuId;
    private String mealTime;
    private int count;
}
