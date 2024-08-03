package com.example.ai.Repository;

import com.example.ai.Entity.Meal.FoodMenu;
import com.example.ai.Entity.Review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByFoodMenuIn(List<FoodMenu> foodMenus);
}
