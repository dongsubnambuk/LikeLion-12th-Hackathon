package com.demo.nimn.repository.meal;

import com.demo.nimn.entity.meal.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<FoodMenu, Long> {
}
