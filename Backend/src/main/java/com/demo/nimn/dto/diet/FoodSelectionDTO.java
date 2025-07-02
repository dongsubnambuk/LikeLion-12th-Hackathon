package com.demo.nimn.dto.diet;

import com.demo.nimn.dto.food.FoodDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FoodSelectionDTO {
    private Long foodSelectionId;
    private String userEmail;
    private String foodTime;
    private FoodDTO foodMenu;
    private int count;
}