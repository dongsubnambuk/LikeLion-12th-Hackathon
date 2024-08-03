package com.example.ai.DTO.Review;

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
public class UserDailyMealPlanDTO {
    LocalDate date;
    List<Long> foodMenuIds;
}
