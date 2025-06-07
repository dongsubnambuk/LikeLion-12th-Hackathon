package com.demo.nimn.repository.review;

import com.demo.nimn.entity.meal.FoodMenu;
import com.demo.nimn.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByFoodMenuIn(List<FoodMenu> foodMenus);
}
