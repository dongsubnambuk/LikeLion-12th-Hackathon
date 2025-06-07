package com.demo.nimn.dao.payment;

import com.demo.nimn.entity.payment.OrderEntity;

public interface OrderDAO {
    public void createOrder(OrderEntity orderEntity);
    public OrderEntity readOrder(String orderId);
    public void deleteOrder(OrderEntity orderEntity);
}
