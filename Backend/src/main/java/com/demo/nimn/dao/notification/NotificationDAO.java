package com.demo.nimn.dao.notification;

import com.demo.nimn.dto.notification.NotificationDTO;
import com.demo.nimn.dto.notification.response.ResponseDTO;
import com.demo.nimn.enums.NotificationType;

import java.util.List;

public interface NotificationDAO {
    NotificationDTO save(NotificationType notificationType, String userEmail, String content, Long dailyReviewId);
    NotificationDTO asRead(Long notificationId);
    ResponseDTO allAsRead(String userEmail);
    int countUnreadNotifications(String userEmail);
    List<NotificationDTO> getAllNotificationsByUserEmail(String userEmail);
}
