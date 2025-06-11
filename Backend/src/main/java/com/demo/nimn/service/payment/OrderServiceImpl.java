package com.demo.nimn.service.payment;

import com.demo.nimn.dao.payment.OrderDAO;
import com.demo.nimn.dto.payment.OrderDTO;
import com.demo.nimn.dto.payment.OrderResponseDTO;
import com.demo.nimn.entity.payment.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{
    private final OrderDAO orderDAO;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public OrderResponseDTO createOrder(OrderDTO orderDTO) {
        Order order = toOrderEntity(orderDTO);
        orderDAO.createOrder(order);
        return toOrderResponseDTO(order);
    }

    @Override
    public Order findByOrderId(String orderId) {
        return orderDAO.readOrder(orderId);
    }

    @Override
    public OrderResponseDTO deleteOrder(Order order) {
        orderDAO.deleteOrder(order);
        return new OrderResponseDTO("success", null);
    }

    public Order toOrderEntity(OrderDTO orderDTO){
        return Order.builder()
                .orderId(orderDTO.getOrderId())
                .purchaser(orderDTO.getPurchaser())
                .totalPrice(orderDTO.getTotalPrice())
                .weeklyId(orderDTO.getWeeklyId())
                .build();
    }

    public OrderDTO toOrderDTO(Order order){
        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .purchaser(order.getPurchaser())
                .totalPrice(order.getTotalPrice())
                .weeklyId(order.getWeeklyId())
                .dateTime(order.getDateTime())
                .build();
    }

    public OrderResponseDTO toOrderResponseDTO(Order order){
        return OrderResponseDTO.builder()
                .result("success")
                .data(toOrderDTO(order))
                .build();
    }
}
