package com.example.webstore.controller;

import com.example.webstore.model.dto.OrderDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

  // Создание нового заказа
  @PostMapping
  public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDTO) {
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
  @PostMapping("/{orderId}/status")
  public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long orderId,
      @RequestBody String newStatus) {
    return null;
  }
}