package com.demo.nimn.dto.food;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MealSelectionDTO {
    private Long id;
    private Long userId;
    private Long dailyDietId;
    private Long foodMenuId;
    private String mealTime;
    private int count;
}
