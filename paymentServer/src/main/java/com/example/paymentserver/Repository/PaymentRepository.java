package com.example.paymentserver.Repository;

import com.example.paymentserver.Entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
    List<PaymentEntity> findByPurchaser(String purchaser);
    Boolean existsByPaymentUid(String paymentUid);
}
