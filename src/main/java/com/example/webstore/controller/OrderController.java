package com.example.webstore.controller;

import com.example.webstore.model.dto.OrderDto;
import com.example.webstore.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  // Получение информации о заказе по его ID
  @GetMapping("/{orderId}")
  public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
    return null;
  }

  // Получение истории заказов пользователя
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<OrderDto>> getOrderHistoryByUser(@PathVariable Long userId) {
    return null;
  }


  // Добавление книги в корзину
  @PostMapping("/in-cart/{bookId}")
  public ResponseEntity<OrderDto> addToCart(@PathVariable Long bookId) {
    return ResponseEntity.ok(orderService.addToCart(bookId));
  }

  // Удаление книги из корзины
  @DeleteMapping("/in-cart/{bookId}")
  public ResponseEntity<Void> removeFromCart(@PathVariable Long bookId) {
    orderService.removeFromCart(bookId);
    return ResponseEntity.ok().build();
  }

  // Очистка корзины от всех книг
  @DeleteMapping("/empty-cart")
  public ResponseEntity<Void> clearCart() {
    orderService.clearCart();
    return ResponseEntity.ok().build();
  }

  // Подтверждение заказа
  @PostMapping("/confirmation")
  public ResponseEntity<OrderDto> confirmOrder() {
    return ResponseEntity.ok(orderService.confirmOrder());
  }

  // Отмена заказа по id
  @DeleteMapping("/cancellation/{orderId}")
  public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
    orderService.cancelOrder(orderId);
    return ResponseEntity.ok().build();
  }
}