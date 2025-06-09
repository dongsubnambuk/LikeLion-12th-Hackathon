package com.demo.nimn.dao.notification;

import com.example.notificationserver.DTO.DietNotificationDTO;
import com.example.notificationserver.DTO.PaymentNotificationDTO;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PaymentNotificationDAO {
    PaymentNotificationDTO create(PaymentNotificationDTO paymentNotificationDTO);
    void updateLastPaymentDate(PaymentNotificationDTO paymentNotificationDTO, LocalDateTime newPaymentDate);
    //Optional<PaymentNotificationDTO> findLatestByEmail(String email);
    PaymentNotificationDTO update(PaymentNotificationDTO paymentNotificationDTO);
    void updateNotificationContentAndTime(PaymentNotificationDTO paymentNotificationDTO);
    void delete(Long id);
}
