package com.example.webstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.example.webstore.model.entity.Book;
import com.example.webstore.model.mapper.BookMapper;
import com.example.webstore.repository.BookRepository;
import com.example.webstore.service.impl.BookServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

  public static final Long BOOK_ID = 1L;

  @Mock
  private BookRepository bookRepository;

  @Mock
  private BookMapper bookMapper;

  @InjectMocks
  private BookServiceImpl bookService;

  /**
   * Тест на обработку случая, когда книга не найдена. Ожидается, что при вызове метода getBookById
   * с несуществующим ID будет выброшено исключение EntityNotFoundException.
   */
  @Test
  void getBookByIdWhenBookIsNotFound() {

    doReturn(Optional.empty()).when(bookRepository)
        .findById(BOOK_ID);

    assertThatThrownBy(() -> bookService.getBookById(BOOK_ID))
        .isInstanceOf(EntityNotFoundException.class);
  }

  /**
   * Тест на обработку случая, когда книга найдена. Ожидается, что при вызове метода getBookById с
   * существующим ID будет возвращен объект книги.
   */
  @Test
  void getBookByIdWhenBookIsFound() {
    Book book = new Book();
    book.setId(BOOK_ID);

    doReturn(Optional.of(book)).when(bookRepository)
        .findById(BOOK_ID);

    Book result = bookService.getBookById(BOOK_ID);

    assertThat(result).isNotNull();
    assertThat(result.getId()).isEqualTo(BOOK_ID);
  }

  @AfterEach
  void verifyInteractions() {
    verify(bookRepository).findById(BOOK_ID);
    verifyNoMoreInteractions(bookRepository, bookMapper);
  }
}