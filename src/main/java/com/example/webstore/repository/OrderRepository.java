package com.example.webstore.repository;

import com.example.webstore.model.entity.Order;
import com.example.webstore.model.enums.OrderStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

  Optional<Order> findByUserIdAndStatus(Long userId, OrderStatus orderStatus);

  Optional<Order> findByIdAndUserIdAndStatus(Long orderId, Long userId, OrderStatus orderStatus);

  @Query("""
      FROM Order o
      LEFT JOIN FETCH o.books
      WHERE o.user.id = :userId AND o.status = :status
      """)
  Optional<Order> findByUserIdAndStatusWithBooks(@Param("userId") Long userId,
      @Param("status") OrderStatus status);
}