package com.demo.nimn.service.order;

import com.demo.nimn.dto.order.OrderDTO;
import com.demo.nimn.entity.order.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);
    Order getOrder(String orderId);
    List<String> getUnpaidPurchasersThisWeek(LocalDateTime startDate, LocalDateTime endDate);
    void payOrder(String orderId);
    void cancelOrder(String orderId);
    void deleteOrder(Order order);
}
