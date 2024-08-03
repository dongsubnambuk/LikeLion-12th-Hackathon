package com.example.ai.Repository;

import com.example.ai.Entity.Meal.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<FoodMenu, Long> {
    Boolean existsByFoodMenuId(Long foodMenuId);
}
