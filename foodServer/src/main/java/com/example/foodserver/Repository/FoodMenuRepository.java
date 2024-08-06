package com.example.foodserver.Repository;

import com.example.foodserver.Entity.FoodMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodMenuRepository extends JpaRepository<FoodMenuEntity, Long> {
}
