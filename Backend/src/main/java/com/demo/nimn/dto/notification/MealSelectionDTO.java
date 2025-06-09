package com.demo.nimn.dto.notification;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MealSelectionDTO {
    private int mealSelectionId;
    private String userEmail;
    private String mealTime;
    private FoodMenuDTO foodMenu;
    private int count;

    @Builder
    public MealSelectionDTO(int mealSelectionId, String userEmail, String mealTime, FoodMenuDTO foodMenu, int count) {
        this.mealSelectionId = mealSelectionId;
        this.userEmail = userEmail;
        this.mealTime = mealTime;
        this.foodMenu = foodMenu;
        this.count = count;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @ToString
    public static class FoodMenuDTO {
        private int id;
        private String name;
        private String image;
        private String price;
        private String main1;
        private String main2;
        private String side1;
        private String side2;
        private String side3;
        private String calories;
        private String carbohydrate;
        private String protein;
        private String fat;
        private String sugar;
        private String sodium;

        @Builder
        public FoodMenuDTO(int id, String name, String image, String price, String main1, String main2, String side1, String side2, String side3, String calories, String carbohydrate, String protein, String fat, String sugar, String sodium) {
            this.id = id;
            this.name = name;
            this.image = image;
            this.price = price;
            this.main1 = main1;
            this.main2 = main2;
            this.side1 = side1;
            this.side2 = side2;
            this.side3 = side3;
            this.calories = calories;
            this.carbohydrate = carbohydrate;
            this.protein = protein;
            this.fat = fat;
            this.sugar = sugar;
            this.sodium = sodium;
        }
    }
}
