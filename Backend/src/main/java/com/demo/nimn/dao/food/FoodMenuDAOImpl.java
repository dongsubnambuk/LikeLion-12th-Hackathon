package com.demo.nimn.dao.food;

import com.demo.nimn.dto.food.FoodMenuDTO;
import com.demo.nimn.entity.food.UserFoodMenu;
import com.demo.nimn.repository.food.FoodMenuRepository;
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
    public FoodMenuDTO create(FoodMenuDTO foodMenuDTO) {
        UserFoodMenu entity = foodMenuEntity(foodMenuDTO);
        UserFoodMenu savedEntity = foodMenuRepository.save(entity);
        return foodMenuDTO(savedEntity);
    }

    @Override
    public Optional<FoodMenuDTO> getById(Long id) {
        return foodMenuRepository.findById(id).map(this::foodMenuDTO);
    }

    @Override
    public List<FoodMenuDTO> getAll() {
        return foodMenuRepository.findAll().stream()
                .map(this::foodMenuDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FoodMenuDTO update(Long id, FoodMenuDTO foodMenuDTO) {
        if (foodMenuRepository.existsById(id)) {
            UserFoodMenu entity = foodMenuEntity(foodMenuDTO);
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

    private UserFoodMenu foodMenuEntity(FoodMenuDTO dto) {
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

    private FoodMenuDTO foodMenuDTO(UserFoodMenu entity) {
        return FoodMenuDTO.builder()
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
