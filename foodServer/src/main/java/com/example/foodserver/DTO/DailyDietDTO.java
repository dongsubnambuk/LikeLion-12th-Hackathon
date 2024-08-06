package com.example.foodserver.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DailyDietDTO {
    private Long id;
    private String dayOfWeek;
    private String mealTime;
    private int count;
    private Long foodMenuId;
}
