package com.demo.nimn.dao.meal;

import com.demo.nimn.entity.food.Food;
import com.demo.nimn.repository.meal.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FoodDAOImpl implements FoodDAO {
    private final FoodRepository foodRepository;

    @Autowired
    public FoodDAOImpl(FoodRepository dietRepository) {
        this.foodRepository = dietRepository;
    }

    @Override
    public Boolean createMeal(Food food) {
        foodRepository.save(food);

        return foodRepository.existsById(food.getId());
    }

    @Override
    public List<Food> findAll(){
        return foodRepository.findAll();
    }

    @Override
    public Food findById(Long foodMenuId){
        return foodRepository.getReferenceById(foodMenuId);
    }
}
