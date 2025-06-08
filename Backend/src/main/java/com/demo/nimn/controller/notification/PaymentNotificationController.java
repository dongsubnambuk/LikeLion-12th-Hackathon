package com.demo.nimn.controller.notification;

import com.example.notificationserver.DTO.PaymentNotificationDTO;
import com.example.notificationserver.Service.NotificationService;
import com.example.notificationserver.Service.NotificationServiceImpl;
import com.example.notificationserver.Service.PaymentNotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification/payment")
@CrossOrigin(origins = {"http://nimn.store", "http://localhost:3000"})
public class PaymentNotificationController {
    private final PaymentNotificationService paymentNotificationService;


    public PaymentNotificationController(PaymentNotificationService paymentNotificationService, NotificationService notificationService) {
        this.paymentNotificationService = paymentNotificationService;
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentNotificationDTO> createDietNotification(@RequestBody PaymentNotificationDTO paymentNotificationDTO) {
        PaymentNotificationDTO createdPaymentNotification = paymentNotificationService.createPaymentNotification(paymentNotificationDTO);
        return ResponseEntity.ok(createdPaymentNotification);
    }
    @DeleteMapping
    public ResponseEntity<Void> deletePaymentNotification(@RequestParam Long id) {
        paymentNotificationService.deletePaymentNotification(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<PaymentNotificationDTO> updatePaymentNotification(@RequestBody PaymentNotificationDTO paymentNotificationDTO) {
        PaymentNotificationDTO updatedPaymentNotification = paymentNotificationService.updatePaymentNotification(paymentNotificationDTO);
        return ResponseEntity.ok(updatedPaymentNotification);
    }
}
