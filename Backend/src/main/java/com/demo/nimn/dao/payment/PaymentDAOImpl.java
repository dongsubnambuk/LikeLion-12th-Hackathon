package com.demo.nimn.dao.payment;

import com.demo.nimn.entity.payment.Payment;
import com.demo.nimn.repository.payment.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PaymentDAOImpl implements PaymentDAO{
    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentDAOImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void createPayment(Payment payment) {
        payment.updateDateTime(LocalDateTime.now());
        paymentRepository.save(payment);
    }

    @Override
    public List<Payment> readPaymentByPurchaser(String purchaser) {
        return paymentRepository.findByPurchaser(purchaser);
    }

    @Override
    public Payment readPaymentById(String paymentId) {
        return paymentRepository.getReferenceById(paymentId);
    }

    @Override
    public void deletePaymentById(String paymentId) {
        paymentRepository.deleteById(paymentId);
    }

    @Override
    public Boolean existsByPaymentUid(String paymentUid) {
        return paymentRepository.existsByPaymentUid(paymentUid);
    }

    @Override
    public Boolean existsByPaymentId(String paymentId) {
        return paymentRepository.existsByPaymentUid(paymentId);
    }

    @Override
    public List<String> findPurchasersThisWeek(){
        LocalDateTime startOfWeek = LocalDateTime.now().with(java.time.DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);
        return paymentRepository.findPurchasersThisWeek(startOfWeek, endOfWeek);
    }
}
