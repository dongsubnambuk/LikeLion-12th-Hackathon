package com.example.notificationserver.Service;

import com.example.notificationserver.DTO.ExternalDietNotificationDTO;
import com.example.notificationserver.DTO.ExternalPaymentNotificationDTO;

public interface NotificationSender {

    ExternalDietNotificationDTO fetchDietNotificationNotification(String url);
    void sendDietNotificationNotification(ExternalDietNotificationDTO dietNotificationDTO);
    void sendPaymentNotificationNotification(ExternalPaymentNotificationDTO externalDTO);
    ExternalPaymentNotificationDTO fetchPaymentNotificationNotification(String url);

}
