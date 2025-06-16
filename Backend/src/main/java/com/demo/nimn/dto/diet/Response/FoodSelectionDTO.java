package com.demo.nimn.dto.diet.Response;

import com.demo.nimn.dto.meal.FoodMenuDTO;
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
    private FoodMenuDTO foodMenu;
    private int count;
}