package com.demo.nimn.dao.payment;

import com.demo.nimn.entity.payment.Order;

public interface OrderDAO {
    public void createOrder(Order order);
    public Order readOrder(String orderId);
    public void deleteOrder(Order order);
}
