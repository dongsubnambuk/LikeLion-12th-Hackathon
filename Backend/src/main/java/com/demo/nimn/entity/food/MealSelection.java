package com.demo.nimn.entity.food;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "meal_selection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MealSelection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mealSelectionId;
    @Column
    private String userEmail;
    @Column
    private Long foodMenuId;
    @Column
    private String mealTime;
    @Column
    private int count;
}