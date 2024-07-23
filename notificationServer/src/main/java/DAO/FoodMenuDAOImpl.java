package DAO;

import DTO.FoodMenuDTO;
import Entity.FoodMenuEntity;
import Repository.FoodMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FoodMenuDAOImpl implements FoodMenuDAO {
    private final FoodMenuRepository foodMenuRepository;

    @Autowired
    public FoodMenuDAOImpl(FoodMenuRepository foodMenuRepository){
        this.foodMenuRepository = foodMenuRepository;
    }
    //Entity To DTO
    private FoodMenuDTO foodMenuEntityToDTO(FoodMenuEntity foodMenuEntity) {
        return FoodMenuDTO.builder()
                .id(foodMenuEntity.getId())
                .userId(foodMenuEntity.getUserId())
                .notificationContent(foodMenuEntity.getNotificationContent())
                .notificationTime(foodMenuEntity.getNotificationTime())
                .build();
    }

    @Override
    public FoodMenuDTO create(FoodMenuDTO foodMenuDTO) {
        FoodMenuEntity foodMenuEntity = FoodMenuEntity.builder()
                .userId(foodMenuDTO.getUserId())
                .notificationContent(foodMenuDTO.getNotificationContent())
                .notificationTime(foodMenuDTO.getNotificationTime())
                .build();
        foodMenuRepository.save(foodMenuEntity);
        return foodMenuDTO;
    }

    @Override
    public FoodMenuDTO findById(Long id) {
        FoodMenuEntity foodMenuEntity = foodMenuRepository.findById(id).orElse(null);
        if (foodMenuEntity == null){
            return null;
        }
        return foodMenuEntityToDTO(foodMenuEntity);
    }

    @Override
    public List<FoodMenuDTO> findByUserId(Long userId) {
        List<FoodMenuEntity> foodMenuEntities = foodMenuRepository.findByUserId(userId);
        return foodMenuEntities.stream()
                .map(this::foodMenuEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FoodMenuDTO update(FoodMenuDTO foodMenuDTO) {
        FoodMenuEntity foodMenuEntity = foodMenuRepository.findById(foodMenuDTO.getId()).orElse(null);
        if(foodMenuEntity != null){
            foodMenuEntity.setNotificationContent(foodMenuDTO.getNotificationContent());
            foodMenuEntity.setNotificationTime(foodMenuDTO.getNotificationTime());
            foodMenuRepository.save(foodMenuEntity);
        }
        return foodMenuDTO;
    }

    @Override
    public void delete(Long id) {
        foodMenuRepository.deleteById(id);
    }
}
