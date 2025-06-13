package com.demo.nimn.dao.notification;

import com.demo.nimn.dto.notification.NotificationDTO;
import com.demo.nimn.dto.notification.response.ResponseDTO;
import com.demo.nimn.entity.notification.Notification;
import com.demo.nimn.enums.NotificationType;
import com.demo.nimn.repository.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationDAOImpl implements NotificationDAO {
    private final NotificationRepository notificationRepository;

    @Override
    public NotificationDTO save(NotificationType notificationType, String userEmail, String content, Long dailyReviewId) {
        return notificationRepository.save(Notification.builder()
                        .userEmail(userEmail)
                        .content(content)
                        .type(notificationType)
                        .dailyReviewId(dailyReviewId)
                        .build())
                .toNotificationDTO(notificationRepository.countByUserEmailAndCheckIsFalse(userEmail));
    }

    @Override
    public NotificationDTO asRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new RuntimeException("Notification Not Found"));
        notification.setCheck(Boolean.TRUE);
        return notificationRepository.save(notification).toNotificationDTO(0);
    }

    @Override
    public ResponseDTO allAsRead(String userEmail) {
        List<Notification> notifications = notificationRepository.findAllByUserEmailAndCheckIsFalse(userEmail);
        for(Notification notification : notifications) {
            notification.setCheck(Boolean.TRUE);
            notificationRepository.save(notification);
        }
        return ResponseDTO.builder()
                .userEmail(userEmail)
                .count(notifications.size())
                .build();
    }

    @Override
    public int countUnreadNotifications(String userEmail) {
        return notificationRepository.countByUserEmailAndCheckIsFalse(userEmail);
    }

    @Override
    public List<NotificationDTO> getAllNotificationsByUserEmail(String userEmail) {
        return notificationRepository.findAllByUserEmail(userEmail);
    }
}