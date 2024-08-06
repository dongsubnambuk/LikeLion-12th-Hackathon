package com.example.paymentserver.DAO;

import com.example.paymentserver.Entity.PaymentEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentDAO {
    public void createPayment(PaymentEntity paymentEntity);
    public List<PaymentEntity> readPaymentByPurchaser(String purchaser);
    public PaymentEntity readPaymentById(String paymentId);
    public void deletePaymentById(String paymentId);
    public Boolean existsByPaymentUid(String paymentUid);
    public Boolean existsByPaymentId(String paymentId);
    public List<String> findPurchasersThisWeek();
}
