package com.demo.nimn.dto.diet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyDietRequestDTO {
    private Long dailyDietId;
    private LocalDate date;
    private List<FoodSelectionRequestDTO> foodSelections;
    private String userEmail;
}
