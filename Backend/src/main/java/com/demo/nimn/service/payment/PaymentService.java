package com.demo.nimn.service.payment;

import com.demo.nimn.dto.payment.*;
import com.demo.nimn.entity.payment.OrderEntity;

import java.util.List;

public interface PaymentService {
    public PaymentResponseDTO createImportPayment(PaymentRequestDTO request);
    public PaymentResponseDTO createPayment(OrderEntity orderEntity, String paymentUid);
    public PaymentResponseDTO readPaymentByPaymentId(String paymentId);
    public PaymentResponseDTOS readPaymentByPurchaser(String purchaser);
    public UserDTO readNonPurchasersThisWeek();
    public PaymentResponseDTO deletePayment(String paymentId);
}
