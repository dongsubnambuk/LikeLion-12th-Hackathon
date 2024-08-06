package com.example.foodserver.DAO;

import com.example.foodserver.DTO.FoodMenuDTO;
import com.example.foodserver.Entity.FoodMenuEntity;
import com.example.foodserver.Repository.FoodMenuRepository;
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
        FoodMenuEntity entity = foodMenuEntity(foodMenuDTO);
        FoodMenuEntity savedEntity = foodMenuRepository.save(entity);
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
            FoodMenuEntity entity = foodMenuEntity(foodMenuDTO);
            entity.setId(id); // Set the ID for the entity
            FoodMenuEntity updatedEntity = foodMenuRepository.save(entity);
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

    private FoodMenuEntity foodMenuEntity(FoodMenuDTO dto) {
        return FoodMenuEntity.builder()
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

    private FoodMenuDTO foodMenuDTO(FoodMenuEntity entity) {
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
