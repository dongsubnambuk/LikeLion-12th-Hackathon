package com.demo.nimn.service.notification;

import com.demo.nimn.dto.notification.NotificationDTO;
import com.demo.nimn.dto.notification.response.ResponseDTO;

import java.util.List;

public interface NotificationService {
    NotificationDTO markAsRead(Long notificationId);
    ResponseDTO markAllAsRead(String userEmail);
    ResponseDTO countUnreadNotifications(String userEmail);
    List<NotificationDTO> getAllNotificationsByUserEmail(String userEmail);
}
