package com.demo.nimn.scheduler.notification;

import com.example.notificationserver.DTO.*;
import com.example.notificationserver.Entity.ExternalDietInfoEntity;
import com.example.notificationserver.Entity.ExternalPaymentInfoEntity;
import com.example.notificationserver.Repository.ExternalDietInfoRepository;
import com.example.notificationserver.Repository.ExternalPaymentInfoRepository;
import com.example.notificationserver.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RestController
@RequestMapping("/api/notification/lol")
public class NotificationSchedulerImpl implements NotificationScheduler{

    private final DietNotificationService dietNotificationService;
    private final NotificationService notificationService;
    private final PaymentNotificationService paymentNotificationService;

    private final ExternalPaymentInfoRepository externalPaymentInfoRepository;
    private final CommunicationService communicationService;
    private final ExternalDietInfoRepository externalDietInfoRepository;

    @Autowired
    public NotificationSchedulerImpl(DietNotificationService dietNotificationService, NotificationService notificationService, PaymentNotificationService paymentNotificationService, ExternalPaymentInfoRepository externalPaymentInfoRepository, CommunicationService communicationService, ExternalDietInfoRepository externalDietInfoRepository) {
        this.dietNotificationService = dietNotificationService;
        this.notificationService = notificationService;
        this.paymentNotificationService = paymentNotificationService;
        this.externalPaymentInfoRepository = externalPaymentInfoRepository;
        this.communicationService = communicationService;
        this.externalDietInfoRepository = externalDietInfoRepository;
    }

    // 초 분 시 일 월 요일에 자동으로 실행되는 메서드
    //"0 0 7, 12, 23 * * ?" 7시 12시 23시
    //"0/20 * * * * ?" 20초마다 실행
    //토요일
    @Override
    @GetMapping("/run-diet-task")
    @Scheduled(cron = "0 0 7 * * ?") // 아침 알림: 매일 7시에 실행
    public void scheduleBreakfastNotification() {
        LocalDate today = LocalDate.now();
        communicationService.getUserDietEmails(today.toString());
        System.out.println("투데이" + today);
        sendDietNotification("아침");
    }

    @Override
    @Scheduled(cron = "0 0 12 * * ?") // 점심 알림: 매일 12시에 실행
    public void scheduleLunchNotification() {
        sendDietNotification("점심");
    }

    @Override
    @Scheduled(cron = "0 0 18 * * ?") // 저녁 알림: 매일 18시에 실행
    public void scheduleDinnerNotification() {
        sendDietNotification("저녁");
    }

    @Override
    public void sendDietNotification(String mealTime) {
        LocalDate today = LocalDate.now();
        List<ExternalDietInfoEntity> diets = externalDietInfoRepository.findByDate(today.toString());

        for (ExternalDietInfoEntity diet : diets) {
            boolean shouldNotify = false;
            if ("아침".equals(mealTime) && !diet.isProcessedBreakfast() && diet.getBreakfast() != null) {
                shouldNotify = true;
            } else if ("점심".equals(mealTime) && !diet.isProcessedLunch() && diet.getLunch() != null) {
                shouldNotify = true;
            } else if ("저녁".equals(mealTime) && !diet.isProcessedDinner() && diet.getDinner() != null) {
                shouldNotify = true;
            }

            if (shouldNotify) {
                LocalDateTime currentDate = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일");
                String formattedCurrentDate = currentDate.format(formatter);

                StringBuilder combinedContent = new StringBuilder();

                switch (mealTime) {
                    case "아침" -> combinedContent.append(formattedCurrentDate).append(" 아침 식단은 ").append(diet.getBreakfast());
                    case "점심" -> combinedContent.append(formattedCurrentDate).append(" 점심 식단은 ").append(diet.getLunch());
                    case "저녁" -> combinedContent.append(formattedCurrentDate).append(" 저녁 식단은 ").append(diet.getDinner());
                }

                combinedContent.append(" 입니다~");

                DietNotificationDTO notification = new DietNotificationDTO();
                notification.setEmail(diet.getEmail());
                notification.setNotificationContent(combinedContent.toString());
                notification.setNotificationTime(currentDate);

                dietNotificationService.createDietNotification(notification);
                notificationService.sendNotification(notification);

                switch (mealTime) {
                    case "아침" -> diet.setProcessedBreakfast(true);
                    case "점심" -> diet.setProcessedLunch(true);
                    case "저녁" -> diet.setProcessedDinner(true);
                }
                externalDietInfoRepository.save(diet);
            }
        }
    }

    @Override
    @GetMapping("/run-payment-task")
    @Scheduled(cron = "0 0 19 ? * SAT") // 결제일 알림
    //@Scheduled(cron = "0/30 * * * * ?")
    public void scheduledPaymentNotificationTasks() {
        ExternalPaymentNotificationDTO userEmailsDTO = communicationService.getUserEmails();
        if (userEmailsDTO != null) {
            communicationService.saveToExternalPaymentInfoEntity(userEmailsDTO);
        }
        schedulePaymentNotification();
    }
    @Override
    public void schedulePaymentNotification() {
        LocalDateTime currentDate = LocalDateTime.now();

        List<ExternalPaymentInfoEntity> pendingNotifications = externalPaymentInfoRepository.findByProcessedFalseAndDate(LocalDate.now());

        for (ExternalPaymentInfoEntity paymentInfo : pendingNotifications) {
            String email = paymentInfo.getEmail();
            // 새로운 알림 생성
            PaymentNotificationDTO newNotification = PaymentNotificationDTO.builder()
                    .email(email)
                    .notificationContent("결제일이 얼마 남지 않았습니다.")
                    .notificationTime(currentDate)  // 현재 시각으로 설정
                    .build();

            // 알림 전송
            notificationService.sendPaymentNotification(newNotification);

            // 새로운 알림 저장
            paymentNotificationService.createPaymentNotification(newNotification);

            // processed를 true로 업데이트
            paymentInfo.setProcessed(true);
            externalPaymentInfoRepository.save(paymentInfo);
        }
    }

    //설문 조사
    @Override
    @GetMapping("/run-survey-task")
    @Scheduled(cron = "0 0 19 * * ?") // 매일 19시에 실행
    public void scheduledSurveyNotification() {
        communicationService.fetchAndSaveDailyReviews();
    }
}