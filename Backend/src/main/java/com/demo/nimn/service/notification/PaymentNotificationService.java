package com.demo.nimn.service.notification;

import com.example.notificationserver.DTO.PaymentNotificationDTO;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PaymentNotificationService {
    PaymentNotificationDTO createPaymentNotification(PaymentNotificationDTO paymentNotificationDTO);
    void deletePaymentNotification(Long id);
    PaymentNotificationDTO updatePaymentNotification(PaymentNotificationDTO paymentNotificationDTO);
    //Optional<PaymentNotificationDTO> findLatestByEmail(String email);
    //void updateLastPaymentDate(PaymentNotificationDTO paymentNotificationDTO, LocalDateTime newPaymentDate);
}
