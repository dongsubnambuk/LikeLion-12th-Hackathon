package com.demo.nimn.repository.meal;

import com.example.ai.Entity.Meal.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<FoodMenu, Long> {
    Boolean existsByFoodMenuId(Long foodMenuId);
}
