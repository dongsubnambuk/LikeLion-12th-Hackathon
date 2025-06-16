package com.demo.nimn.dao.diet;

import com.demo.nimn.dto.diet.FoodNutritionDTO;
import com.demo.nimn.entity.diet.UserFoodMenu;
import com.demo.nimn.repository.diet.FoodMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class FoodMenuDAOImpl implements FoodMenuDAO {

    private final FoodMenuRepository foodMenuRepository;

    @Autowired
    public FoodMenuDAOImpl(FoodMenuRepository foodMenuRepository) {
        this.foodMenuRepository = foodMenuRepository;
    }

    @Override
    public FoodNutritionDTO create(FoodNutritionDTO foodNutritionDTO) {
        UserFoodMenu entity = foodMenuEntity(foodNutritionDTO);
        UserFoodMenu savedEntity = foodMenuRepository.save(entity);
        return foodMenuDTO(savedEntity);
    }

    @Override
    public Optional<FoodNutritionDTO> getById(Long id) {
        return foodMenuRepository.findById(id).map(this::foodMenuDTO);
    }

    @Override
    public List<FoodNutritionDTO> getAll() {
        return foodMenuRepository.findAll().stream()
                .map(this::foodMenuDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FoodNutritionDTO update(Long id, FoodNutritionDTO foodNutritionDTO) {
        if (foodMenuRepository.existsById(id)) {
            UserFoodMenu entity = foodMenuEntity(foodNutritionDTO);
            entity.setId(id); // Set the ID for the entity
            UserFoodMenu updatedEntity = foodMenuRepository.save(entity);
            return foodMenuDTO(updatedEntity);
        }
        throw new RuntimeException("FoodMenu not found " + id);
    }

    @Override
    public void delete(Long id) {
        if (foodMenuRepository.existsById(id)) {
            foodMenuRepository.deleteById(id);
        } else {
            throw new RuntimeException("FoodMenu not found  " + id);
        }
    }

    private UserFoodMenu foodMenuEntity(FoodNutritionDTO dto) {
        return UserFoodMenu.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .calories(dto.getCalories())
                .image(dto.getImage())
                .carbohydrate(dto.getCarbohydrate())
                .protein(dto.getProtein())
                .fat(dto.getFat())
                .sugar(dto.getSugar())
                .sodium(dto.getSodium())
                .build();
    }

    private FoodNutritionDTO foodMenuDTO(UserFoodMenu entity) {
        return FoodNutritionDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .calories(entity.getCalories())
                .image(entity.getImage())
                .carbohydrate(entity.getCarbohydrate())
                .protein(entity.getProtein())
                .fat(entity.getFat())
                .sugar(entity.getSugar())
                .sodium(entity.getSodium())
                .build();
    }
}
