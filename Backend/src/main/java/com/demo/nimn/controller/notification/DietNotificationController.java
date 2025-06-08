package com.demo.nimn.controller.notification;

import com.example.notificationserver.DTO.DietNotificationDTO;
import com.example.notificationserver.DTO.ExternalPaymentNotificationDTO;
import com.example.notificationserver.Scheduler.NotificationScheduler;
import com.example.notificationserver.Service.CommunicationService;
import com.example.notificationserver.Service.DietNotificationService;
import com.example.notificationserver.Service.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/notification/diet")
public class DietNotificationController {
    private final DietNotificationService dietNotificationService;
    private final NotificationServiceImpl notificationServiceImpl;
    private final CommunicationService communicationService;
    private NotificationScheduler notificationScheduler;
    @Autowired
    public DietNotificationController(DietNotificationService dietNotificationService, NotificationServiceImpl notificationServiceImpl, CommunicationService communicationService) {
        this.dietNotificationService = dietNotificationService;
        this.notificationServiceImpl = notificationServiceImpl;
        this.communicationService = communicationService;
    }

    @PostMapping("/create")
    public ResponseEntity<DietNotificationDTO> createDietNotification(@RequestBody DietNotificationDTO dietNotificationDTO) {
        DietNotificationDTO createdDietNotification = dietNotificationService.createDietNotification(dietNotificationDTO);
        notificationServiceImpl.sendNotification(createdDietNotification); // 알림 전송
        return ResponseEntity.ok(createdDietNotification);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DietNotificationDTO> getDietNotificationById(@PathVariable Long id) {
        DietNotificationDTO dietNotificationDTO = dietNotificationService.getDietNotificationById(id);
        return ResponseEntity.ok(dietNotificationDTO);
    }

    @GetMapping("/run-diet-task")
    public ResponseEntity<String> runScheduledTask() {
        try {
            notificationScheduler.scheduleBreakfastNotification();
            return ResponseEntity.ok("Task executed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Task execution failed: " + e.getMessage());
        }
    }

    @GetMapping("/run-payment-task")
    public ResponseEntity<String> runScheduledPaymentTask() {
        try {
            notificationScheduler.scheduledPaymentNotificationTasks();
            return ResponseEntity.ok("Task executed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Task execution failed: " + e.getMessage());
        }
    }

    @GetMapping("/run-survey-task")
    public ResponseEntity<String> runSurveyTask() {
        LocalDate date = LocalDate.now();
        try {
            communicationService.getUserDietEmails(date.toString());
            return ResponseEntity.ok("Task executed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Task execution failed: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DietNotificationDTO> updateDietNotification(@PathVariable Long id, @RequestBody DietNotificationDTO dietNotificationDTO) {
        dietNotificationDTO.setId(id);
        DietNotificationDTO updatedDietNotification = dietNotificationService.updateDietNotification(dietNotificationDTO);
        notificationServiceImpl.sendNotification(updatedDietNotification); // 알림 전송
        return ResponseEntity.ok(updatedDietNotification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDietNotification(@PathVariable Long id) {
        dietNotificationService.deleteDietNotification(id);
        return ResponseEntity.noContent().build();
    }
}