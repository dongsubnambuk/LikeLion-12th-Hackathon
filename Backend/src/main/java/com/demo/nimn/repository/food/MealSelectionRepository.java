package com.demo.nimn.repository.food;

import com.demo.nimn.entity.food.MealSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealSelectionRepository extends JpaRepository<MealSelection, Long> {
    List<MealSelection> findByUserEmail(String userEmail);
}