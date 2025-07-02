package com.demo.nimn.controller.notification;

import com.demo.nimn.dto.notification.NotificationDTO;
import com.demo.nimn.dto.notification.response.NotificationCountDTO;
import com.demo.nimn.enums.NotificationType;
import com.demo.nimn.service.notification.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "알림 API", description = "알림 전송, 체크, 조회")
@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "알림 조회(GetAllNotifications)", description = "이메일을 보내면 해당 사용자의 전체 알림을 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "알림 조회 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "입력된 Email 형식이 잘못되었거나 유효하지 않은 값입니다.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content
            )
    })
    @GetMapping("/all")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications(@Parameter(description = "유저 이메일", example = "user@example.com")
                                                                        @RequestParam String userEmail) {
        return  ResponseEntity.ok(notificationService.getAllNotificationsByUserEmail(userEmail));
    }

    @Operation(summary = "알림 읽음 처리(MarkAsRead)", description = "알림 ID를 보내면 해당 알림을 읽음 처리")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "알림 읽은 처리 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "입력된 알림 ID 형식이 잘못되었거나 유효하지 않은 값입니다.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content
            )
    })
    @PatchMapping("/{notificationId}")
    public ResponseEntity<NotificationDTO> markAsRead(@Parameter(description = "알림 ID", example = "1")
                                                        @PathVariable Long notificationId) {
        return  ResponseEntity.ok(notificationService.markAsRead(notificationId));
    }

    @Operation(summary = "모든 알림 읽음 처리(MarkAllAsRead)", description = "이메일을 보내면 해당 사용자의 모든 알림을 읽음 처리")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "모든 알림 읽음 처리 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationCountDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "입력된 Email 형식이 잘못되었거나 유효하지 않은 값입니다.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content
            )
    })
    @PatchMapping
    public ResponseEntity<NotificationCountDTO> markAllAsRead(@Parameter(description = "유저 이메일", example = "user@example.com")
                                                                @RequestParam String userEmail) {
        return  ResponseEntity.ok(notificationService.markAllAsRead(userEmail));
    }

    @Operation(summary = "안읽은 알림 갯수 조회(CountUnreadNotifications)", description = "이메일을 보내면 해당 사용자의 안읽은 알림 갯수를 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "안읽은 알림 갯수 조회 성공",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotificationCountDTO.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "입력된 Email 형식이 잘못되었거나 유효하지 않은 값입니다.",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content
            )
    })
    @GetMapping
    public ResponseEntity<NotificationCountDTO> countUnreadNotifications(@Parameter(description = "유저 이메일", example = "user@example.com")
                                                                            @RequestParam String userEmail) {
        return ResponseEntity.ok(notificationService.countUnreadNotifications(userEmail));
    }

    @Operation(summary = "테스트용")
    @GetMapping("/test")
    public ResponseEntity<NotificationCountDTO> test(@RequestParam NotificationType notificationType,
                                                     @RequestParam String userEmail,
                                                     @RequestParam String content,
                                                     @RequestParam Long dailyReviewId) {
        return ResponseEntity.ok(notificationService.testNotification(notificationType, userEmail, content, dailyReviewId));
    }
}
