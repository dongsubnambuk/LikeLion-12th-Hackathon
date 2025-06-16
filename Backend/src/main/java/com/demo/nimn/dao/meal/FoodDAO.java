package com.demo.nimn.dao.meal;

import com.demo.nimn.entity.meal.FoodMenu;

import java.util.List;

public interface FoodDAO {
    public Boolean createMeal(FoodMenu foodMenu);
    public List<FoodMenu> findAll();
    public FoodMenu findById(Long foodMenuId);
}
