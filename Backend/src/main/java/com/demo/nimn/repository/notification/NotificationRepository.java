package com.demo.nimn.repository.notification;

import com.demo.nimn.dto.notification.NotificationDTO;
import com.demo.nimn.entity.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Optional<Integer> countByUserEmailAndCheckIsFalse(String userEmail);

    @Query("select new com.demo.nimn.dto.notification.NotificationDTO(n.notificationId, n.content, n.type, n.sendTime, n.dailyReviewId, n.check, 0) from Notification n where n.userEmail = :userEmail")
    List<NotificationDTO> findAllByUserEmail(String userEmail);

    List<Notification> findAllByUserEmailAndCheckIsFalse(String userEmail);
}
