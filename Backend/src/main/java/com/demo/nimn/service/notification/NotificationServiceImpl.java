package com.demo.nimn.service.notification;

import com.example.notificationserver.DTO.DailyReviewDTO;
import com.example.notificationserver.DTO.DietNotificationDTO;
import com.example.notificationserver.DTO.PaymentNotificationDTO;
import com.example.notificationserver.DTO.SurveyNotificationDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void sendNotification(DietNotificationDTO notification) {
        messagingTemplate.convertAndSend("/topic/notification/diet/" + notification.getEmail(), notification);
    }
    @Override
    public void sendPaymentNotification(PaymentNotificationDTO notification) {
        messagingTemplate.convertAndSend("/topic/notification/payment/" + notification.getEmail(), notification);
    }
    @Override
    public void sendSurveyNotification(SurveyNotificationDTO notification) {
        messagingTemplate.convertAndSend("/topic/notification/survey/" + notification.getEmail(), notification);
    }
}