package com.demo.nimn.repository.notification;

import com.example.notificationserver.Entity.SurveyNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyNotificationRepository extends JpaRepository<SurveyNotificationEntity, Long> {
}
