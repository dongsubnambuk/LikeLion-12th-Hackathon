package com.demo.nimn.repository.payment;

import com.demo.nimn.entity.payment.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
    List<PaymentEntity> findByPurchaser(String purchaser);
    Boolean existsByPaymentUid(String paymentUid);
    Boolean existsByPaymentId(String paymentId);
    @Query ("SELECT DISTINCT p.purchaser FROM PaymentEntity p WHERE p.dateTime >= :startDate AND p.dateTime <= :endDate")
    List<String> findPurchasersThisWeek(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
