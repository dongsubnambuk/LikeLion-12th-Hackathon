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
public class WeeklyDietDTO {
    private Long weeklyId;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<DailyDietDTO> dailyDiets;
    private String userEmail;
}