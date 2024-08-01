package com.example.paymentserver.Service;

import com.example.paymentserver.DTO.PaymentRequestDTO;
import com.example.paymentserver.DTO.PaymentResponseDTO;
import com.example.paymentserver.DTO.PaymentResponseDTOS;
import com.example.paymentserver.Entity.OrderEntity;

public interface PaymentService {
    public PaymentResponseDTO createImportPayment(PaymentRequestDTO request);
    public PaymentResponseDTO createPayment(OrderEntity orderEntity, String paymentUid);
    public PaymentResponseDTO readPaymentByPaymentId(String paymentId);
    public PaymentResponseDTOS readPaymentByPurchaser(String purchaser);
    public PaymentResponseDTO deletePayment(String paymentId);
}
