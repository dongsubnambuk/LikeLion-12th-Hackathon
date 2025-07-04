package com.demo.nimn.dto.diet;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FoodSelectionCreateDTO {
    private Long foodSelectionId;
    private String foodTime;
    private Long foodId;
    private int count;
    private String userEmail;
}