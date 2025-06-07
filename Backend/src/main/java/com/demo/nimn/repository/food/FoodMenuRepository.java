package com.demo.nimn.repository.food;

import com.demo.nimn.entity.food.UserFoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodMenuRepository extends JpaRepository<UserFoodMenu, Long> {
}
