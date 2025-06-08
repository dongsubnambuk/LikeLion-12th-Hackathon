package com.demo.nimn.controller.notification;

import com.example.notificationserver.DTO.ExternalDietNotificationDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/sendNotification")
    @SendTo("/topic/notifications")
    public ExternalDietNotificationDTO sendNotification(ExternalDietNotificationDTO notification) {
        return notification;
    }
}
