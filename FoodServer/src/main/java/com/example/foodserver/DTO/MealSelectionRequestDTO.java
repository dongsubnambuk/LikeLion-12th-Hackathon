package com.example.foodserver.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MealSelectionRequestDTO {
    private Long mealSelectionId;
    private String mealTime;
    private Long foodMenuId;
    private int count;
    private String userEmail;
}