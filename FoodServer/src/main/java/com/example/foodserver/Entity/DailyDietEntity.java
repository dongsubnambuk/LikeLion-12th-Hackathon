package com.example.foodserver.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "daily_diet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyDietEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dailyDietId;

    private String dayOfWeek;
    private Long foodMenuId;
    private Long userId;

    @OneToMany(mappedBy = "dailyDiet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealSelectionEntity> mealSelections;
}
