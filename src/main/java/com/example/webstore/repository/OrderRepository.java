package com.example.webstore.repository;

import com.example.webstore.model.entity.Order;
import com.example.webstore.model.enums.OrderStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

  Optional<Order> findByUserIdAndStatus(Long userId, OrderStatus orderStatus);

  Optional<Order> findByIdAndUserIdAndStatus(Long orderId, Long userId, OrderStatus orderStatus);
}