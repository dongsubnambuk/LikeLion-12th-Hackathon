package com.demo.nimn.dao.meal;

import com.demo.nimn.entity.food.Food;

import java.util.List;

public interface FoodDAO {
    public Boolean createMeal(Food food);
    public List<Food> findAll();
    public Food findById(Long foodMenuId);
}
