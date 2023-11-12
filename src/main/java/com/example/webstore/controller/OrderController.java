package com.example.webstore.controller;

import com.example.webstore.model.dto.OrderDto;
import com.example.webstore.model.dto.OrderInfoDto;
import com.example.webstore.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;

  // Создание нового заказа
  @PostMapping
  public ResponseEntity<OrderDto> createOrder(@RequestBody OrderInfoDto orderDto) {
    return null;
  }

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

  // Выполнение оплаты заказа
  @PostMapping("/{orderId}/pay")
  public ResponseEntity<OrderDto> payOrder(@PathVariable Long orderId) {
    return null;
  }

  // Обновление статуса заказа
  @PutMapping("/{orderId}/status")
  public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long orderId,
      @RequestBody String newStatus) {
    return null;
  }

  // Удаление заказа
  @DeleteMapping("/{orderId}")
  public ResponseEntity<Void> removeOrderById(@PathVariable Long orderId) {
//    orderService.delete(orderId);
    return ResponseEntity.ok().build();
  }



  // Добавление книги в корзину
  @PostMapping("/in-cart/{bookId}")
  public ResponseEntity<OrderInfoDto> addToCart(@PathVariable Long bookId) {
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
}