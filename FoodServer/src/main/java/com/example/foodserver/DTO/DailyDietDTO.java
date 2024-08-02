package com.example.foodserver.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DailyDietDTO {
    private Long dailyId;
    private String dayOfWeek;
    private Long foodMenuId;
}
