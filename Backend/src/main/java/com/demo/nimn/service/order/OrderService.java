package com.demo.nimn.service.order;

import com.demo.nimn.dto.order.OrderDTO;
import com.demo.nimn.entity.order.Order;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO);
    Order getOrder(String orderId);
    void payOrder(String orderId);
    void cancelOrder(String orderId);
    void deleteOrder(Order order);
}
