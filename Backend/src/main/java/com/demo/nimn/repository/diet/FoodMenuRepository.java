package com.demo.nimn.repository.diet;

import com.demo.nimn.entity.diet.UserFoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodMenuRepository extends JpaRepository<UserFoodMenu, Long> {
}
