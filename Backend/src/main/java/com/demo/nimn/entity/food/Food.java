package com.demo.nimn.entity.food;

import com.demo.nimn.dto.food.FoodDTO;
import com.demo.nimn.entity.review.ReviewSummary;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_food_menu")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private String price;

    private String main1;

    private String main2;

    private String side1;

    private String side2;

    private String side3;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private NutritionFact nutritionFact;

    @OneToOne(mappedBy = "food", cascade = CascadeType.ALL)
    private ReviewSummary reviewSummary;

    public FoodDTO toFoodDTO() {
        return FoodDTO.builder()
                .id(this.getId())
                .name(this.getName())
                .image(this.getImage())
                .price(this.getPrice())
                .main1(this.getMain1())
                .main2(this.getMain2())
                .side1(this.getSide1())
                .side2(this.getSide2())
                .side3(this.getSide3())
                .calories(this.getNutritionFact().getCalories())
                .carbohydrate(this.getNutritionFact().getCarbohydrate())
                .protein(this.getNutritionFact().getProtein())
                .fat(this.getNutritionFact().getFat())
                .sugar(this.getNutritionFact().getSugar())
                .sodium(this.getNutritionFact().getSodium())
                .build();
    }
}
