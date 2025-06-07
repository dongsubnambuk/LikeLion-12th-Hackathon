package com.demo.nimn.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWeeklyMealPlanDTO {
    String userEmail;
    List<UserDailyMealPlanDTO> userDailyMealPlans;
}
