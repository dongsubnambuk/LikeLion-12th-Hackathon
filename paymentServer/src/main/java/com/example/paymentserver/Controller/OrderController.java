package com.example.paymentserver.Controller;

import com.example.paymentserver.DTO.OrderDTO;
import com.example.paymentserver.DTO.OrderResponseDTO;
import com.example.paymentserver.Service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment/order")
public class OrderController {
    private final OrderService orderService;
    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/newOrder")
    public OrderResponseDTO createOrder(@RequestBody OrderDTO orderDTO){
        return orderService.createOrder(orderDTO);
    }
}
