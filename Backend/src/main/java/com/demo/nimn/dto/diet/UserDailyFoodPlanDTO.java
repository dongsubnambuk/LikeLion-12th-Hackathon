package com.demo.nimn.dto.diet;

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
public class UserDailyFoodPlanDTO {
    LocalDate date;
    List<Long> foodMenuIds;
}