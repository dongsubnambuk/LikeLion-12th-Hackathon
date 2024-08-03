package com.example.paymentserver.Service;

import com.example.paymentserver.DTO.PaymentRequestDTO;
import com.example.paymentserver.DTO.PaymentResponseDTO;
import com.example.paymentserver.DTO.PaymentResponseDTOS;
import com.example.paymentserver.DTO.UserDTO;
import com.example.paymentserver.Entity.OrderEntity;

import java.util.List;

public interface PaymentService {
    public PaymentResponseDTO createImportPayment(PaymentRequestDTO request);
    public PaymentResponseDTO createPayment(OrderEntity orderEntity, String paymentUid);
    public PaymentResponseDTO readPaymentByPaymentId(String paymentId);
    public PaymentResponseDTOS readPaymentByPurchaser(String purchaser);
    public UserDTO readNonPurchasersThisWeek();
    public PaymentResponseDTO deletePayment(String paymentId);
}
