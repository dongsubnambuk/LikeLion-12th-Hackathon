package com.demo.nimn.repository.diet;

import com.demo.nimn.entity.diet.FoodSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodSelectionRepository extends JpaRepository<FoodSelection, Long> {
    List<FoodSelection> findByUserEmail(String userEmail);
}