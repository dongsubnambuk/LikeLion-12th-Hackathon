package com.demo.nimn.service.payment;

import com.example.paymentserver.DTO.OrderDTO;
import com.example.paymentserver.DTO.OrderResponseDTO;
import com.example.paymentserver.Entity.OrderEntity;

public interface OrderService {
    public OrderResponseDTO createOrder(OrderDTO orderDTO);
    public OrderEntity findByOrderId(String orderId);
    public OrderResponseDTO deleteOrder(OrderEntity orderEntity);
}
