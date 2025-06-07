package com.demo.nimn.dto.food.Request;

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
public class WeeklyDietRequestDTO {
    private Long weeklyId;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<DailyDietRequestDTO> dailyDiets;
    private String userEmail;
}
