package com.demo.nimn.service.payment;

import com.demo.nimn.dto.payment.OrderDTO;
import com.demo.nimn.dto.payment.OrderResponseDTO;
import com.demo.nimn.entity.payment.OrderEntity;

public interface OrderService {
    public OrderResponseDTO createOrder(OrderDTO orderDTO);
    public OrderEntity findByOrderId(String orderId);
    public OrderResponseDTO deleteOrder(OrderEntity orderEntity);
}
