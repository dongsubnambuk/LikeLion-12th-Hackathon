package com.demo.nimn.service.notification;

import com.example.notificationserver.DTO.DietNotificationDTO;

import java.util.List;

public interface DietNotificationService {
    DietNotificationDTO createDietNotification(DietNotificationDTO dietNotificationDTO);

    DietNotificationDTO getDietNotificationById(Long id);

    DietNotificationDTO updateDietNotification(DietNotificationDTO dietNotificationDTO);

    void deleteDietNotification(Long id);

    String getLatestDietNotificationToString();
}
