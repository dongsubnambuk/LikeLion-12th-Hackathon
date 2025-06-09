package com.demo.nimn.repository.notification;

import com.example.notificationserver.Entity.DietNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DietNotificationRepository extends JpaRepository<DietNotificationEntity, Long> {
    DietNotificationEntity findTopByOrderByNotificationTime();
}
