package com.example.webstore.service.impl;

import com.example.webstore.repository.OrderRepository;
import com.example.webstore.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  @Override
  public void delete(Long orderId) {
    orderRepository.delete(orderRepository.findById(orderId)
        .orElseThrow(() -> new EntityNotFoundException("Заказ не найден")));
  }
}