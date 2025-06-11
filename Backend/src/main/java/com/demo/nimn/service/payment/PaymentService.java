package com.demo.nimn.service.payment;

import com.demo.nimn.dto.payment.*;
import com.demo.nimn.entity.payment.Order;

public interface PaymentService {
    public PaymentResponseDTO createImportPayment(PaymentRequestDTO request);
    public PaymentResponseDTO createPayment(Order order, String paymentUid);
    public PaymentResponseDTO readPaymentByPaymentId(String paymentId);
    public PaymentResponseDTOS readPaymentByPurchaser(String purchaser);
    public UserDTO readNonPurchasersThisWeek();
    public PaymentResponseDTO deletePayment(String paymentId);
}
