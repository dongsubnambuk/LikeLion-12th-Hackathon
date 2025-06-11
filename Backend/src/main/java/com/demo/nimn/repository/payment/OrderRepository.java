package com.demo.nimn.repository.payment;

import com.demo.nimn.entity.payment.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
