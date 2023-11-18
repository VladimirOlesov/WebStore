package com.example.webstore.service;

import static com.example.webstore.model.entity.Author.Fields.authorName;
import static com.example.webstore.model.entity.Genre.Fields.genreName;
import static com.example.webstore.model.entity.Order.Fields.books;
import static com.example.webstore.model.entity.Order.Fields.status;
import static com.example.webstore.model.entity.Order.Fields.user;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.webstore.IntegrationTestBase;
import com.example.webstore.model.dto.OrderDto;
import com.example.webstore.model.entity.Author;
import com.example.webstore.model.entity.Book;
import com.example.webstore.model.entity.Genre;
import com.example.webstore.model.entity.Order;
import com.example.webstore.model.entity.User;
import com.example.webstore.model.enums.OrderStatus;
import com.example.webstore.model.enums.Role;
import com.example.webstore.repository.BookRepository;
import com.example.webstore.repository.OrderRepository;
import com.example.webstore.repository.UserRepository;
import com.example.webstore.service.impl.OrderServiceImpl;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@WithMockUser(username = "testUser", authorities = "USER")
class OrderServiceIT extends IntegrationTestBase {

  private final OrderServiceImpl orderService;

  private final OrderRepository orderRepository;

  private final UserRepository userRepository;

  private final BookRepository bookRepository;

  private Book testBook;
  private User testUser;

  private User createTestUser() {

    return userRepository.save(
        User.builder()
            .username("testUser")
            .email("testEmail")
            .password("testPassword")
            .role(Role.USER)
            .build());
  }

  private Book createTestBook(String title) {

    return bookRepository.save(Book.builder()
        .title(title)
        .author(Author.builder().authorName(authorName).build())
        .genre(Genre.builder().genreName(genreName).build())
        .ISBN("ISBN")
        .deleted(false)
        .build());
  }

  @Test
  void GetCart() {

    Order testOrder = orderRepository.save(
        Order.builder()
            .user(createTestUser())
            .status(OrderStatus.IN_CART)
            .build());

    Order resultOrder = orderService.getCart();

    assertThat(resultOrder)
        .isNotNull()
        .isEqualTo(testOrder);
  }

  @Test
  void AddToCart() {
    testBook = createTestBook("testBook");
    createTestUser();
    OrderDto orderDto = orderService.addToCart(testBook.getId());

    Order updatedOrder = orderRepository.findById(orderDto.orderId()).orElse(null);

    assertThat(updatedOrder).isNotNull();
    assertThat(updatedOrder.getStatus()).isEqualTo(OrderStatus.IN_CART);
    assertThat(updatedOrder.getBooks()).contains(testBook);
  }

  @Test
  void removeFromCartIfHaveOneBook() {
    testUser = createTestUser();
    testBook = createTestBook("testBook");
    Order testOrder = orderRepository.save(
        Order.builder()
            .user(testUser)
            .books(new HashSet<>(Set.of(testBook)))
            .status(OrderStatus.IN_CART)
            .build());

    orderService.removeFromCart(testBook.getId());

    Order shouldBeNull = orderRepository.findById(testOrder.getId()).orElse(null);

    assertThat(shouldBeNull).isNull();
  }

  @Test
  void removeFromCartIfHaveMoreOneBook() {
    testUser = createTestUser();
    testBook = createTestBook("testBook");
    Book testBookMore = createTestBook("testBookMore");
    Order testOrder = orderRepository.save(
        Order.builder()
            .user(testUser)
            .books(new HashSet<>(Set.of(testBook, testBookMore)))
            .status(OrderStatus.IN_CART)
            .build());

    orderService.removeFromCart(testBook.getId());

    Order updatedOrder = orderRepository.findById(testOrder.getId()).orElse(null);

    assertThat(updatedOrder)
        .isNotNull()
        .hasFieldOrPropertyWithValue(user, testUser)
        .hasFieldOrPropertyWithValue(status, OrderStatus.IN_CART)
        .hasFieldOrPropertyWithValue(books, testOrder.getBooks());

    assertThat(updatedOrder.getBooks()).doesNotContain(testBook);
  }

  @Test
  void clearCart() {
  }

  @Test
  void confirmOrder() {
  }

  @Test
  void cancelOrder() {
  }
}