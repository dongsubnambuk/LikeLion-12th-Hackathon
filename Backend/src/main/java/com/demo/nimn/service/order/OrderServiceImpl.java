package com.demo.nimn.service.order;

import com.demo.nimn.dto.order.OrderDTO;
import com.demo.nimn.entity.order.Order;
import com.demo.nimn.repository.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        // TODO-jh: WeeklyDiet 조회한 후에 총 가격 계산
        
        Order order = convertToOrder(orderDTO);
        Order saved = orderRepository.save(order);
        return convertToOrderDTO(saved);
    }

    @Override
    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
    }

    @Override
    public List<String> getUnpaidPurchasersThisWeek(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findUnpaidPurchasersThisWeek(startDate, endDate);
    }

    @Override
    public void payOrder(String orderId) {
        Order order = getOrder(orderId);
        order.pay();
        orderRepository.save(order);
    }

    @Override
    public void cancelOrder(String orderId) {
        Order order = getOrder(orderId);
        order.cancel();
        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    // Entity <-> DTO 변환
    public Order convertToOrder(OrderDTO orderDTO) {
        return Order.builder()
                .purchaser(orderDTO.getPurchaser())
                .totalPrice(orderDTO.getTotalPrice())
                .weeklyDietId(orderDTO.getWeeklyDietId())
                .build();
    }

    public OrderDTO convertToOrderDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .purchaser(order.getPurchaser())
                .totalPrice(order.getTotalPrice())
                .weeklyDietId(order.getWeeklyDietId())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}