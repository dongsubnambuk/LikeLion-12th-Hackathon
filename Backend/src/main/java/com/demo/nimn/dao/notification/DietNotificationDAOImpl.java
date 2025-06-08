package com.demo.nimn.dao.notification;

import com.example.notificationserver.DTO.DietNotificationDTO;
import com.example.notificationserver.Entity.DietNotificationEntity;
import com.example.notificationserver.Repository.DietNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DietNotificationDAOImpl implements DietNotificationDAO {
    private final DietNotificationRepository dietNotificationRepository;

    @Autowired
    public DietNotificationDAOImpl(DietNotificationRepository dietNotificationRepository, JdbcTemplate jdbcTemplate){
        this.dietNotificationRepository = dietNotificationRepository;
    }
    //Entity To DTO
    private DietNotificationDTO DietNotificationEntityToDTO(DietNotificationEntity dietNotificationEntity) {
        return DietNotificationDTO.builder()
                .id(dietNotificationEntity.getId())
                .email(dietNotificationEntity.getEmail())
                .notificationContent(dietNotificationEntity.getNotificationContent())
                .notificationTime(dietNotificationEntity.getNotificationTime())
                .build();
    }

    @Override
    public DietNotificationDTO create(DietNotificationDTO dietNotificationDTO) {
        DietNotificationEntity dietNotificationEntity = DietNotificationEntity.builder()
                .email(dietNotificationDTO.getEmail())
                .notificationContent(dietNotificationDTO.getNotificationContent())
                .notificationTime(dietNotificationDTO.getNotificationTime())
                .build();
        dietNotificationRepository.save(dietNotificationEntity);
        return dietNotificationDTO;
    }

    @Override
    public DietNotificationDTO findById(Long id) {
        DietNotificationEntity dietNotificationEntity = dietNotificationRepository.findById(id).orElse(null);
        if (dietNotificationEntity == null){
            return null;
        }
        return DietNotificationEntityToDTO(dietNotificationEntity);
    }


    @Override
    public DietNotificationDTO update(DietNotificationDTO dietNotificationDTO) {
        DietNotificationEntity dietNotificationEntity = dietNotificationRepository.findById(dietNotificationDTO.getId()).orElse(null);
        if(dietNotificationEntity != null){
            dietNotificationEntity.setNotificationContent(dietNotificationDTO.getNotificationContent());
            dietNotificationEntity.setNotificationTime(dietNotificationDTO.getNotificationTime());
            dietNotificationRepository.save(dietNotificationEntity);
        }
        return dietNotificationDTO;
    }

    @Override
    public void delete(Long id) {
        dietNotificationRepository.deleteById(id);
    }

}
