package com.demo.nimn.scheduler.notification;

import com.example.notificationserver.DAO.PaymentNotificationDAO;
import com.example.notificationserver.DTO.*;
import com.example.notificationserver.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public interface NotificationScheduler {
    void scheduleBreakfastNotification();
    void scheduleLunchNotification();
    void scheduleDinnerNotification();
    void sendDietNotification(String mealTime);
    void scheduledPaymentNotificationTasks();
    void schedulePaymentNotification();
    void scheduledSurveyNotification();
}