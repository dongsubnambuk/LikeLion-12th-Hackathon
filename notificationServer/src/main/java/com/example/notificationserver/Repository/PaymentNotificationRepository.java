package com.example.notificationserver.Repository;

import com.example.notificationserver.Entity.PaymentNotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentNotificationRepository extends JpaRepository<PaymentNotificationEntity, Long> {
    List<PaymentNotificationEntity> findByUserId(Long userId);

    Optional<PaymentNotificationEntity> findFirstByEmailOrderByLastPaymentDateDesc(String email);
}
