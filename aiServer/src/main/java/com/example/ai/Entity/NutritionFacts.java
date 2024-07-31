package com.example.ai.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.ConditionalOnGraphQlSchema;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionFacts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column
    String calories;
    @Column
    String carbohydrate;
    @Column
    String protein;
    @Column
    String fat;
    @Column
    String sugar;
    @Column
    String sodium;
}
