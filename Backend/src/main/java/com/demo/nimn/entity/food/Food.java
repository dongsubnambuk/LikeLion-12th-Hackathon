package com.demo.nimn.entity.food;

import com.demo.nimn.entity.review.ReviewSummary;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_food")
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
}
