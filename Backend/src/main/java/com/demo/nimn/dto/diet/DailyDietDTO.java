package com.demo.nimn.dto.diet;

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
    private String userEmail;
    private LocalDate date;
    private List<FoodSelectionDTO> foodSelections;
}