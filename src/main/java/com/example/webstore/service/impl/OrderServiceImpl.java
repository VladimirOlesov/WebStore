package com.example.webstore.service.impl;

import com.example.webstore.exception.DuplicateException;
import com.example.webstore.model.dto.OrderInfoDto;
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
  public Order getOrderByUserIdAndStatus(OrderStatus status) {
    User user = userService.getAuthenticatedUserByUsername();
    return orderRepository.findByUserIdAndStatus(user.getId(), status)
        .orElseThrow(() -> new EntityNotFoundException("Корзина не найдена"));
  }

//  @Override
//  public void delete(Long orderId) {
//    orderRepository.delete(orderRepository.findById(orderId)
//        .orElseThrow(() -> new EntityNotFoundException("Заказ не найден")));
//  }

  @Override
  @Transactional
  public OrderInfoDto addToCart(Long bookId) {
    User user = userService.getAuthenticatedUserByUsername();

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

    return orderMapper.toOrderDto(cartOrder, bookId);
  }

  @Override
  @Transactional
  public void removeFromCart(Long bookId) {
    Order cartOrder = getOrderByUserIdAndStatus(OrderStatus.IN_CART);
    Book bookToRemove = bookService.getBookById(bookId);

    if (!cartOrder.getBooks().contains(bookToRemove)) {
      throw new EntityNotFoundException("Книга не найдена в корзине");
    }

    cartOrder.getBooks().remove(bookToRemove);
    orderRepository.save(cartOrder);
  }

  @Override
  @Transactional
  public void clearCart() {
    Order cartOrder = getOrderByUserIdAndStatus(OrderStatus.IN_CART);

    if (cartOrder.getBooks().isEmpty()) {
      throw new EntityNotFoundException("Корзина уже пуста");
    }

    cartOrder.getBooks().clear();
    orderRepository.save(cartOrder);
  }
}