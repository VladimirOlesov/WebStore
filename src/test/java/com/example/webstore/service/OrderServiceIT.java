package com.example.webstore.service;

import static com.example.webstore.model.entity.Author.Fields.authorName;
import static com.example.webstore.model.entity.Genre.Fields.genreName;
import static com.example.webstore.model.entity.Order.Fields.books;
import static com.example.webstore.model.entity.Order.Fields.orderDate;
import static com.example.webstore.model.entity.Order.Fields.status;
import static com.example.webstore.model.entity.Order.Fields.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.webstore.IntegrationTestBase;
import com.example.webstore.exception.DuplicateException;
import com.example.webstore.model.dto.OrderDto;
import com.example.webstore.model.entity.Author;
import com.example.webstore.model.entity.Book;
import com.example.webstore.model.entity.Genre;
import com.example.webstore.model.entity.Order;
import com.example.webstore.model.entity.User;
import com.example.webstore.model.enums.OrderStatus;
import com.example.webstore.model.enums.Role;
import com.example.webstore.repository.AuthorRepository;
import com.example.webstore.repository.BookRepository;
import com.example.webstore.repository.GenreRepository;
import com.example.webstore.repository.OrderRepository;
import com.example.webstore.repository.UserRepository;
import com.example.webstore.service.impl.OrderServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
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

  private final AuthorRepository authorRepository;

  private final GenreRepository genreRepository;

  private List<Book> testBooks;
  private Order testCart;

  private User createTestUser() {
    return userRepository.save(
        User.builder()
            .username("testUser")
            .email("testEmail")
            .password("testPassword")
            .role(Role.USER)
            .build());
  }

  private List<Book> createTestBooks(String... ISBNs) {
    return Arrays.stream(ISBNs)
        .map(ISBN -> bookRepository.save(Book.builder()
            .title("Title")
            .author(authorRepository.save(
                Author.builder()
                    .authorName(authorName)
                    .build())
            )
            .genre(genreRepository.save(Genre.builder()
                .genreName(genreName)
                .build())
            )
            .ISBN(ISBN)
            .deleted(false)
            .build())).toList();
  }

  private Order createTestCart(String... ISBNs) {
    List<Book> books = createTestBooks(ISBNs);

    return orderRepository.save(
        Order.builder()
            .user(createTestUser())
            .status(OrderStatus.IN_CART)
            .books(new HashSet<>(books))
            .build());
  }

  private Order createConfirmedOrder(LocalDateTime orderDate) {
    return orderRepository.save(
        Order.builder()
            .user(createTestUser())
            .status(OrderStatus.COMPLETED)
            .books(new HashSet<>(createTestBooks("ISBN")))
            .orderDate(orderDate)
            .build());
  }

  @Test
  void GetCartIfCartExists() {
    testCart = createTestCart("ISBN");

    OrderDto orderDto = orderService.getCart();

    assertThat(orderDto).isNotNull();
    assertThat(orderDto.orderDate()).isNull();
    assertThat(orderDto.status()).isEqualTo(testCart.getStatus());
    assertThat(orderDto.orderId()).isEqualTo(testCart.getId());
    assertThat(orderDto.books()).hasSize(1);
    assertThat(orderDto.books())
        .contains(testCart.getBooks().stream().findFirst().orElse(null));
  }

  @Test
  void GetCartIfCartNotExist() {
    createTestUser();

    OrderDto orderDto = orderService.getCart();

    assertThat(orderDto).isNotNull();
    assertThat(orderDto.orderDate()).isNull();
    assertThat(orderDto.status()).isNull();
    assertThat(orderDto.orderId()).isNull();
    assertThat(orderDto.books()).isEmpty();
  }

  @Test
  void GetCartInternalIfFound() {
    testCart = createTestCart();

    Order resultCart = orderService.getCartInternal();

    assertThat(resultCart)
        .isNotNull()
        .isEqualTo(testCart);
  }

  @Test
  void getCartInternalIfFoundButNotInCartStatus() {
    orderRepository.save(Order.builder()
        .user(createTestUser())
        .status(OrderStatus.COMPLETED)
        .build());

    assertThatThrownBy(orderService::getCartInternal).isInstanceOf(
        EntityNotFoundException.class).hasMessage("Корзина не найдена");
  }

  @Test
  void GetCartInternalIfCartDoesNotFound() {
    createTestUser();

    assertThatThrownBy(orderService::getCartInternal).isInstanceOf(
        EntityNotFoundException.class).hasMessage("Корзина не найдена");
  }

  @Test
  void AddToCartIfCartDoesNotExist() {
    testBooks = createTestBooks("ISBN");

    createTestUser();

    OrderDto orderDto = orderService.addToCart(testBooks.iterator().next().getId());

    Order updatedOrder = orderRepository.findById(orderDto.orderId()).orElse(null);

    assertThat(updatedOrder).isNotNull();
    assertThat(updatedOrder.getOrderDate()).isNull();
    assertThat(updatedOrder.getStatus()).isEqualTo(OrderStatus.IN_CART);
    assertThat(updatedOrder.getBooks()).hasSize(1);
    assertThat(updatedOrder.getBooks()).contains(testBooks.get(0));
  }

  @Test
  void AddToCartIfBookAlreadyIsInCart() {
    Order testCart = createTestCart("ISBN");

    assertThatThrownBy(() -> orderService.addToCart(testCart.getBooks().iterator().next().getId()))
        .isInstanceOf(DuplicateException.class)
        .hasMessage("Книга уже добавлена в корзину");
  }

  @Test
  void addToCartIfCartHasOtherBooks() {
    testCart = createTestCart("ISBN");

    OrderDto orderDto = orderService.addToCart(createTestBooks("OtherISBN").iterator().next()
        .getId());

    assertThat(orderDto).isNotNull();
    assertThat(orderDto.books()).hasSize(2);
    assertThat(orderDto.books()).containsExactlyInAnyOrderElementsOf(testCart.getBooks());
  }

  @Test
  void removeFromCartIfCartHasOneBook() {
    testCart = createTestCart("ISBN");

    orderService.removeFromCart(testCart.getBooks().iterator().next().getId());

    Order shouldBeNull = orderRepository.findById(testCart.getId()).orElse(null);

    assertThat(shouldBeNull).isNull();
  }

  @Test
  void removeFromCartIfCartHasMoreOneBook() {
    testCart = createTestCart("ISBN", "OtherISBN");

    orderService.removeFromCart(testCart.getBooks().stream()
        .filter(book -> "OtherISBN".equals(book.getISBN()))
        .findFirst()
        .map(Book::getId)
        .orElse(null));

    Order updatedOrder = orderRepository.findById(testCart.getId()).orElse(null);

    assertThat(updatedOrder)
        .isNotNull()
        .hasFieldOrPropertyWithValue(user, testCart.getUser())
        .hasFieldOrPropertyWithValue(status, OrderStatus.IN_CART)
        .hasFieldOrPropertyWithValue(books, testCart.getBooks());

    assertThat(updatedOrder.getBooks()).hasSize(1);
    assertThat(updatedOrder.getBooks())
        .extracting(Book::getId)
        .contains(testCart.getBooks().stream()
            .filter(book -> "ISBN".equals(book.getISBN()))
            .map(Book::getId)
            .toArray(Long[]::new));
  }

  @Test
  void removeFromCartIfBookDoesNotFound() {
    testBooks = createTestBooks("ISBN");

    testCart = createTestCart("OtherISBN");

    assertThatThrownBy(() -> orderService.removeFromCart(testBooks.stream()
        .filter(book -> "ISBN".equals(book.getISBN()))
        .findFirst()
        .map(Book::getId)
        .orElse(null)))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("Книга не найдена в корзине");

    Order updatedOrder = orderRepository.findById(testCart.getId()).orElse(null);

    assertThat(updatedOrder).isNotNull();
    assertThat(updatedOrder.getBooks()).hasSize(1);
    assertThat(updatedOrder.getBooks())
        .extracting(Book::getId)
        .contains(testCart.getBooks().stream()
            .filter(book -> "OtherISBN".equals(book.getISBN()))
            .map(Book::getId)
            .toArray(Long[]::new));
  }

  @Test
  void clearCartIfCartExists() {
    testCart = createTestCart("ISBN");

    orderService.clearCart();

    Order shouldBeNull = orderRepository.findById(testCart.getId()).orElse(null);

    assertThat(shouldBeNull).isNull();
  }

  @Test
  void clearCartIfCartDoesNotExist() {
    createTestUser();

    assertThatThrownBy(orderService::clearCart)
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage("Корзина не найдена");
  }

  @Test
  void confirmOrderIfCartExists() {
    testCart = createTestCart("ISBN");

    Order confirmOrder = orderRepository.findById(orderService.confirmOrder().orderId())
        .orElse(null);

    assertThat(confirmOrder)
        .isNotNull()
        .hasFieldOrPropertyWithValue(user, testCart.getUser())
        .hasFieldOrPropertyWithValue(status, OrderStatus.COMPLETED)
        .hasFieldOrPropertyWithValue(books, testCart.getBooks())
        .hasFieldOrProperty(orderDate);

    assertThat(confirmOrder.getBooks()).hasSize(1);
    assertThat(confirmOrder.getBooks())
        .extracting(Book::getId)
        .contains(testCart.getBooks().stream()
            .filter(book -> "ISBN".equals(book.getISBN()))
            .map(Book::getId)
            .toArray(Long[]::new));

    assertThat(confirmOrder.getOrderDate()).isBeforeOrEqualTo(LocalDateTime.now());
  }

  @Test
  void confirmOrderIfCartDoesNotExist() {
    createTestUser();

    assertThatThrownBy(orderService::confirmOrder)
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage("Корзина не найдена");
  }

  @Test
  void cancelOrderIfOrderExistsAndCanBeCancelled() {
    Order confirmedOrder = createConfirmedOrder(LocalDateTime.now().minusDays(1));

    orderService.cancelOrder(confirmedOrder.getId());

    Order checkCancelledOrder = orderRepository.findById(confirmedOrder.getId()).orElse(null);
    assertThat(checkCancelledOrder)
        .isNotNull()
        .hasFieldOrPropertyWithValue(user, confirmedOrder.getUser())
        .hasFieldOrPropertyWithValue(status, OrderStatus.CANCELLED)
        .hasFieldOrPropertyWithValue(books, confirmedOrder.getBooks())
        .hasFieldOrProperty(orderDate);

    assertThat(checkCancelledOrder.getStatus()).isEqualTo(OrderStatus.CANCELLED);
    assertThat(checkCancelledOrder.getOrderDate()).isBeforeOrEqualTo(
        LocalDateTime.now().minusDays(1));
  }

  @Test
  void cancelOrderIfOrderExistsAndCanNotBeCancelled() {
    Order confirmedOrder = createConfirmedOrder(LocalDateTime.now().minusDays(2));

    assertThatThrownBy(() -> orderService.cancelOrder(confirmedOrder.getId()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Срок отмены заказа истек");

    Order checkCancelledOrder = orderRepository.findById(confirmedOrder.getId()).orElse(null);
    assertThat(checkCancelledOrder)
        .isNotNull()
        .hasFieldOrPropertyWithValue(user, confirmedOrder.getUser())
        .hasFieldOrPropertyWithValue(status, OrderStatus.COMPLETED)
        .hasFieldOrPropertyWithValue(books, confirmedOrder.getBooks())
        .hasFieldOrProperty(orderDate);

    assertThat(checkCancelledOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    assertThat(checkCancelledOrder.getOrderDate()).isBeforeOrEqualTo(
        LocalDateTime.now().minusDays(2));
  }

  @Test
  void cancelOrderIfOrderDoesNotExist() {
    createTestUser();

    assertThatThrownBy(() -> orderService.cancelOrder(1L))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage("Завершенный заказ не найден");
  }
}