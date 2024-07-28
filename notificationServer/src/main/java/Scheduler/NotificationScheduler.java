package Scheduler;

import DTO.DietNotificationDTO;
import DTO.ExternalDietNotificationDTO;
import Service.DietNotificationService;
import Service.NotificationSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    private final NotificationSender notificationSender;

    public NotificationScheduler(NotificationSender notificationSender, DietNotificationService dietNotificationService) {
        this.notificationSender = notificationSender;
    }

    @Scheduled(cron = "0 0 7 * * *")
    public void fetchAndStoreDietNotificationNotification() {
        String url = "http://example.com/api/diets"; // 실제 서버 URL
        ExternalDietNotificationDTO externalDTO = notificationSender.fetchDietNotificationNotification(url);

        // 필요한 정보만 추출
        DietNotificationDTO dietNotificationDTO = new DietNotificationDTO();
        dietNotificationDTO.setUserId(externalDTO.getUserId());
        //여기에 내가 생각했던 내용물 저장하면 될 듯? Maybe... 어케하노 시@부럴
        dietNotificationDTO.setNotificationContent(externalDTO.getDiet());
        dietNotificationDTO.setNotificationContent(externalDTO.getNotificationContent());

        //DB에 저장시키기
        notificationSender.sendDietNotificationNotification(externalDTO);
    }
}
