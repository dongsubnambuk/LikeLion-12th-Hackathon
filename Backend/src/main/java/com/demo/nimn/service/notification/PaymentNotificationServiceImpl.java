package com.demo.nimn.service.notification;

import com.example.notificationserver.DAO.PaymentNotificationDAO;
import com.example.notificationserver.DTO.PaymentNotificationDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentNotificationServiceImpl implements PaymentNotificationService{

    private final PaymentNotificationDAO paymentNotificationDAO;

    public PaymentNotificationServiceImpl(PaymentNotificationDAO paymentNotificationDAO) {
        this.paymentNotificationDAO = paymentNotificationDAO;

    }

    @Override
    public PaymentNotificationDTO createPaymentNotification(PaymentNotificationDTO paymentNotificationDTO) {
        return paymentNotificationDAO.create(paymentNotificationDTO);
    }

    @Override
    public PaymentNotificationDTO updatePaymentNotification(PaymentNotificationDTO paymentNotificationDTO) {
        return paymentNotificationDAO.update(paymentNotificationDTO);
    }

    @Override
    public void deletePaymentNotification(Long id) {
        paymentNotificationDAO.delete(id);
    }

}
