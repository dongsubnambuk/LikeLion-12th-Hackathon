package com.example.foodserver.DTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class WeeklyDietDTO {
    private Long weeklyId;
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
}
