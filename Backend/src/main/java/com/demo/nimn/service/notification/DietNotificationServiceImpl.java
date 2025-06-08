package com.demo.nimn.service.notification;

import com.example.notificationserver.DAO.DietNotificationDAO;
import com.example.notificationserver.DTO.DietNotificationDTO;
import com.example.notificationserver.Entity.DietNotificationEntity;
import com.example.notificationserver.Repository.DietNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DietNotificationServiceImpl implements DietNotificationService {

    private final DietNotificationDAO dietNotificationDAO;
    private final DietNotificationRepository dietNotificationRepository;

    public DietNotificationServiceImpl(DietNotificationDAO dietNotificationDAO, NotificationServiceImpl notificationServiceImpl, DietNotificationRepository dietNotificationRepository) {
        this.dietNotificationDAO = dietNotificationDAO;
        this.dietNotificationRepository = dietNotificationRepository;
    }

    @Override
    public DietNotificationDTO createDietNotification(DietNotificationDTO dietNotificationDTO) {
        return dietNotificationDAO.create(dietNotificationDTO);
    }

    @Override
    public DietNotificationDTO getDietNotificationById(Long id) {
        return dietNotificationDAO.findById(id);
    }

    @Override
    public DietNotificationDTO updateDietNotification(DietNotificationDTO dietNotificationDTO) {
        return dietNotificationDAO.update(dietNotificationDTO);
    }

    @Override
    public void deleteDietNotification(Long id) {
        dietNotificationDAO.delete(id);
    }

    @Override
    // 가장 최신의 DietNotificationDTO 반환
    public String getLatestDietNotificationToString() {
        // 최신 레코드를 가져오는 쿼리 사용
        DietNotificationEntity latestDietNotification = dietNotificationRepository.findTopByOrderByNotificationTime();
        System.out.println("Notification email: " + latestDietNotification.getEmail());
        return latestDietNotification.getEmail();
    }

}