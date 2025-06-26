package com.demo.nimn.controller.notification;

import com.demo.nimn.dto.notification.NotificationDTO;
import com.demo.nimn.dto.notification.response.NotificationCountDTO;
import com.demo.nimn.enums.NotificationType;
import com.demo.nimn.service.notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "NOTIFICATION SERVER API", description = "알림 전송, 체크, 조회")
@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "알림 조회(GetAllNotifications)", description = "이메일을 보내면 해당 사용자의 전체 알림을 조회")
    @GetMapping("/all")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications(@RequestParam String userEmail) {
        return  ResponseEntity.ok(notificationService.getAllNotificationsByUserEmail(userEmail));
    }

    @Operation(summary = "알림 읽음 처리(MarkAsRead)", description = "알림 ID를 보내면 해당 알림을 읽음 처리")
    @PatchMapping("/{notificationId}")
    public ResponseEntity<NotificationDTO> markAsRead(@PathVariable Long notificationId) {
        return  ResponseEntity.ok(notificationService.markAsRead(notificationId));
    }

    @Operation(summary = "모든 알림 읽음 처리(MarkAllAsRead)", description = "이메일을 보내면 해당 사용자의 모든 알림을 읽음 처리")
    @PatchMapping
    public ResponseEntity<NotificationCountDTO> markAllAsRead(@RequestParam String userEmail) {
        return  ResponseEntity.ok(notificationService.markAllAsRead(userEmail));
    }

    @Operation(summary = "안읽은 알림 갯수 조회(CountUnreadNotifications)", description = "이메일을 보내면 해당 사용자의 안읽은 알림 갯수를 조회")
    @GetMapping
    public ResponseEntity<NotificationCountDTO> countUnreadNotifications(@RequestParam String userEmail) {
        return ResponseEntity.ok(notificationService.countUnreadNotifications(userEmail));
    }

    @Operation(summary = "테스트용")
    @GetMapping("/test")
    public ResponseEntity<NotificationCountDTO> test(@RequestParam NotificationType notificationType,
                                                     @RequestParam String userEmail,
                                                     @RequestParam String content) {
        return ResponseEntity.ok(notificationService.testNotification(notificationType, userEmail, content));
    }
}
