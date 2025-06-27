package com.demo.nimn.service.order;

import com.demo.nimn.dto.order.OrderDTO;
import com.demo.nimn.entity.diet.DailyDiet;
import com.demo.nimn.entity.diet.FoodSelection;
import com.demo.nimn.entity.diet.WeeklyDiet;
import com.demo.nimn.entity.order.Order;
import com.demo.nimn.repository.diet.WeeklyDietRepository;
import com.demo.nimn.repository.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final WeeklyDietRepository weeklyDietRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            WeeklyDietRepository weeklyDietRepository) {
        this.orderRepository = orderRepository;
        this.weeklyDietRepository = weeklyDietRepository;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        WeeklyDiet weeklyDiet = weeklyDietRepository.findById(orderDTO.getWeeklyDietId())
                .orElseThrow(() -> new RuntimeException("WeeklyDiet not found: " + orderDTO.getWeeklyDietId()));

        orderDTO.setTotalPrice(calculateTotalPrice(weeklyDiet));

        Order order = convertToOrder(orderDTO);

        Order savedOrder = orderRepository.save(order);
        return convertToOrderDTO(savedOrder);
    }

    private Long calculateTotalPrice(WeeklyDiet weeklyDiet) {
        long totalPrice = 0;

        for (DailyDiet dailyDiet : weeklyDiet.getDailyDiets()) {

            for (FoodSelection foodSelection : dailyDiet.getFoodSelections()) {

                String priceStr = foodSelection.getFood().getPrice();
                Long unitPrice = parsePriceString(priceStr);

                totalPrice += unitPrice * foodSelection.getCount();
            }
        }

        return totalPrice;
    }

    private Long parsePriceString(String priceStr) {
        try {
            return Long.parseLong(priceStr.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid price format: " + priceStr);
        }
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