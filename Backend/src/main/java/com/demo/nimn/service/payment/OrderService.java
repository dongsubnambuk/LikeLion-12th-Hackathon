package com.demo.nimn.service.payment;

import com.demo.nimn.dto.payment.OrderDTO;
import com.demo.nimn.dto.payment.OrderResponseDTO;
import com.demo.nimn.entity.payment.Order;

public interface OrderService {
    public OrderResponseDTO createOrder(OrderDTO orderDTO);
    public Order findByOrderId(String orderId);
    public OrderResponseDTO deleteOrder(Order order);
}
