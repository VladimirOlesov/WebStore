package com.example.webstore.service;

import com.example.webstore.model.dto.OrderDto;
import com.example.webstore.model.entity.Order;

public interface OrderService {

  Order getCart();

  OrderDto addToCart(Long bookId);

  void removeFromCart(Long bookId);

  void clearCart();

  OrderDto confirmOrder();

  void cancelOrder(Long orderId);
}