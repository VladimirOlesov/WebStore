package com.example.webstore.service;

import com.example.webstore.model.dto.OrderInfoDto;
import com.example.webstore.model.entity.Order;
import com.example.webstore.model.enums.OrderStatus;

public interface OrderService {

  Order getOrderByUserIdAndStatus(OrderStatus status);

//  void delete(Long orderId);

  OrderInfoDto addToCart(Long bookId);

  void removeFromCart(Long bookId);

  void clearCart();
}