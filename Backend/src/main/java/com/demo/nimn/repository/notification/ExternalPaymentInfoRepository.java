package com.demo.nimn.repository.notification;

import com.example.notificationserver.Entity.ExternalPaymentInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExternalPaymentInfoRepository extends JpaRepository<ExternalPaymentInfoEntity, Long> {
    List<ExternalPaymentInfoEntity> findByDateAndProcessed(LocalDate date, boolean processed);
    List<ExternalPaymentInfoEntity> findByProcessedFalseAndDate(LocalDate date);
}
