package com.example.webstore.service.impl;

import com.example.webstore.exception.DuplicateException;
import com.example.webstore.model.dto.OrderDto;
import com.example.webstore.model.entity.Book;
import com.example.webstore.model.entity.Order;
import com.example.webstore.model.entity.User;
import com.example.webstore.model.enums.OrderStatus;
import com.example.webstore.model.mapper.OrderMapper;
import com.example.webstore.repository.OrderRepository;
import com.example.webstore.service.BookService;
import com.example.webstore.service.OrderService;
import com.example.webstore.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  private final UserService userService;

  private final BookService bookService;

  private final OrderMapper orderMapper;

  @Override
  public OrderDto getCart() {
    User user = userService.getAuthenticatedUser();
    return orderRepository.findByUserIdAndStatusWithBooks(user.getId(), OrderStatus.IN_CART)
        .map(orderMapper::toOrderDto)
        .orElse(OrderDto.builder().books(Set.of()).build());
  }

  @Override
  public Order getCartInternal() {
    User user = userService.getAuthenticatedUser();
    return orderRepository.findByUserIdAndStatus(user.getId(), OrderStatus.IN_CART)
        .orElseThrow(() -> new EntityNotFoundException("Корзина не найдена"));
  }

  @Override
  @Transactional
  public OrderDto addToCart(Long bookId) {
    User user = userService.getAuthenticatedUser();

    Book book = bookService.getBookById(bookId);

    Order cartOrder = orderRepository.findByUserIdAndStatus(user.getId(), OrderStatus.IN_CART)
        .orElseGet(() -> orderRepository.save(Order.builder()
            .user(user)
            .status(OrderStatus.IN_CART)
            .build()));

    if (cartOrder.getBooks().contains(book)) {
      throw new DuplicateException("Книга уже добавлена в корзину");
    }

    cartOrder.getBooks().add(book);
    orderRepository.save(cartOrder);

    return orderMapper.toOrderDto(cartOrder);
  }

  @Override
  @Transactional
  public void removeFromCart(Long bookId) {
    Order cartOrder = getCartInternal();
    Book bookToRemove = bookService.getBookById(bookId);

    if (!cartOrder.getBooks().remove(bookToRemove)) {
      throw new EntityNotFoundException("Книга не найдена в корзине");
    }

    if (cartOrder.getBooks().isEmpty()) {
      orderRepository.delete(cartOrder);
    } else {
      orderRepository.save(cartOrder);
    }
  }

  @Override
  @Transactional
  public void clearCart() {
    orderRepository.delete(getCartInternal());
  }

  @Override
  @Transactional
  public OrderDto confirmOrder() {
    Order cartOrder = getCartInternal();

    cartOrder.setStatus(OrderStatus.COMPLETED);
    cartOrder.setOrderDate(LocalDateTime.now());

    orderRepository.save(cartOrder);

    return orderMapper.toOrderDto(cartOrder);
  }

  @Override
  @Transactional
  public void cancelOrder(Long orderId) {
    User user = userService.getAuthenticatedUser();

    Order confirmedOrder = orderRepository.findByIdAndUserIdAndStatus(orderId, user.getId(),
            OrderStatus.COMPLETED)
        .orElseThrow(() -> new EntityNotFoundException("Завершенный заказ не найден"));

    if (ChronoUnit.DAYS.between(confirmedOrder.getOrderDate(), LocalDateTime.now()) > 1) {
      throw new IllegalArgumentException("Срок отмены заказа истек");
    }

    confirmedOrder.setStatus(OrderStatus.CANCELLED);
    orderRepository.save(confirmedOrder);
  }
}