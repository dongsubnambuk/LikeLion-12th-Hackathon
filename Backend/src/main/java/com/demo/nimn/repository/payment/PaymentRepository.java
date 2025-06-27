package com.demo.nimn.repository.payment;

import com.demo.nimn.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {

    List<Payment> findByPurchaser(String purchaser);
    Boolean existsByUid(String uid);
}
