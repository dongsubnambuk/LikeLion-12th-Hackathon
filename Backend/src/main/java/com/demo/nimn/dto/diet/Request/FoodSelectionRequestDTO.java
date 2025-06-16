package com.demo.nimn.dto.diet.Request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FoodSelectionRequestDTO {
    private Long foodSelectionId;
    private String foodTime;
    private Long foodMenuId;
    private int count;
    private String userEmail;
}