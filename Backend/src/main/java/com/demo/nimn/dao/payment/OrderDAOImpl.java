package com.demo.nimn.dao.payment;

import com.demo.nimn.entity.payment.Order;
import com.demo.nimn.repository.payment.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class OrderDAOImpl implements OrderDAO{
    private final OrderRepository orderRepository;

    @Autowired
    public OrderDAOImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void createOrder(Order order) {
        Map<String, Object> result = new HashMap<>();

        order.updateDateTime(LocalDateTime.now());

        orderRepository.save(order);
    }

    @Override
    public Order readOrder(String orderId) {
        return orderRepository.getReferenceById(orderId);
    }

    @Override
    public void deleteOrder(Order order){
        orderRepository.delete(order);
    }
}
