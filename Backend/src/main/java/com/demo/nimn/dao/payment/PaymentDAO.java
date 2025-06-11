package com.demo.nimn.dao.payment;

import com.demo.nimn.entity.payment.Payment;

import java.util.List;

public interface PaymentDAO {
    public void createPayment(Payment payment);
    public List<Payment> readPaymentByPurchaser(String purchaser);
    public Payment readPaymentById(String paymentId);
    public void deletePaymentById(String paymentId);
    public Boolean existsByPaymentUid(String paymentUid);
    public Boolean existsByPaymentId(String paymentId);
    public List<String> findPurchasersThisWeek();
}
