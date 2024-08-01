package com.example.paymentserver.Service;

import com.example.paymentserver.DAO.OrderDAO;
import com.example.paymentserver.DTO.OrderDTO;
import com.example.paymentserver.DTO.OrderResponseDTO;
import com.example.paymentserver.Entity.OrderEntity;
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
        OrderEntity orderEntity = toOrderEntity(orderDTO);
        orderDAO.createOrder(orderEntity);
        return toOrderResponseDTO(orderEntity);
    }

    @Override
    public OrderEntity findByOrderId(Long orderId) {
        return orderDAO.readOrder(orderId);
    }

    @Override
    public OrderResponseDTO deleteOrder(OrderEntity orderEntity) {
        return new OrderResponseDTO("success", null);
    }

    public OrderEntity toOrderEntity(OrderDTO orderDTO){
        return OrderEntity.builder()
                .orderId(orderDTO.getOrderId())
                .purchaser(orderDTO.getPurchaser())
                .totalPrice(orderDTO.getTotalPrice())
                .weeklyId(orderDTO.getWeeklyId())
                .build();
    }

    public OrderDTO toOrderDTO(OrderEntity orderEntity){
        return OrderDTO.builder()
                .orderId(orderEntity.getOrderId())
                .purchaser(orderEntity.getPurchaser())
                .totalPrice(orderEntity.getTotalPrice())
                .weeklyId(orderEntity.getWeeklyId())
                .dateTime(orderEntity.getDateTime())
                .build();
    }

    public OrderResponseDTO toOrderResponseDTO(OrderEntity orderEntity){
        return OrderResponseDTO.builder()
                .result("success")
                .data(toOrderDTO(orderEntity))
                .build();
    }
}
