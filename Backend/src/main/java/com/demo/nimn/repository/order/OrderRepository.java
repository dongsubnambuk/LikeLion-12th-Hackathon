package com.demo.nimn.repository.order;

import com.demo.nimn.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    // 이번 주에 주문한 모든 유저 조회
    @Query("SELECT DISTINCT o.purchaser FROM Order o WHERE o.createdAt >= :startDate AND o.createdAt <= :endDate")
    List<String> findPurchasersThisWeek(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // 이번 주에 주문했지만 아직 결제하지 않은 유저 조회
    @Query("SELECT DISTINCT o.purchaser FROM Order o WHERE o.createdAt >= :startDate AND o.createdAt <= :endDate AND o.status = 'PENDING'")
    List<String> findUnpaidPurchasersThisWeek(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}