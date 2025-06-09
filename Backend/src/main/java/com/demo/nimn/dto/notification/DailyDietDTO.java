package com.demo.nimn.dto.notification;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DailyDietDTO {
    private int dailyDietId;
    private String userEmail;
    private String date;
    private List<MealSelectionDTO> mealSelections;

    @Builder
    public DailyDietDTO(int dailyDietId, String userEmail, String date, List<MealSelectionDTO> mealSelections) {
        this.dailyDietId = dailyDietId;
        this.userEmail = userEmail;
        this.date = date;
        this.mealSelections = mealSelections;
    }
}
