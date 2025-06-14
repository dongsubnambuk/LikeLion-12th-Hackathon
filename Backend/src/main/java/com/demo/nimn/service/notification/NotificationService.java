package com.demo.nimn.service.notification;

import com.demo.nimn.dto.notification.NotificationDTO;
import com.demo.nimn.dto.notification.response.NotificationCountDTO;

import java.util.List;

public interface NotificationService {
    NotificationDTO markAsRead(Long notificationId);
    NotificationCountDTO markAllAsRead(String userEmail);
    NotificationCountDTO countUnreadNotifications(String userEmail);
    List<NotificationDTO> getAllNotificationsByUserEmail(String userEmail);
}
