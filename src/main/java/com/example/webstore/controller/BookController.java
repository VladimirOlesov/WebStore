package com.example.webstore.controller;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.enums.SortBy;
import com.example.webstore.model.enums.SortDirection;
import com.example.webstore.service.BookService;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

  private final BookService bookService;

  // Получение списка книг по названию, с фильтрацией, сортировкой и пагинацией
  @GetMapping
  public ResponseEntity<Page<BookDto>> getBooks(
      @RequestParam(name = "title", required = false) String title,
      @RequestParam(name = "author", required = false) Long authorId,
      @RequestParam(name = "genre", required = false) Long genreId,
      @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
      @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
      @RequestParam(name = "sortBy", required = false, defaultValue = "TITLE") SortBy sortBy,
      @RequestParam(name = "sortDirection",
          required = false, defaultValue = "ASC") SortDirection sortDirection,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "1") int size) {
    PageRequest pageable = PageRequest.of(page, size);
    Page<BookDto> books = bookService.getBooks(title, authorId, genreId, minPrice, maxPrice,
        sortBy, sortDirection, pageable);
    return ResponseEntity.ok(books);
  }

  // Получение книги по ее id
  @GetMapping("/{bookId}")
  public ResponseEntity<BookDto> getBookById(@PathVariable Long bookId) {
    return ResponseEntity.ok(bookService.getBookDtoById(bookId));
  }

  // Поиск книг по id автора
  @GetMapping("/search/byAuthorId")
  public ResponseEntity<List<BookDto>> searchBooksByAuthor(@RequestParam Long authorId) {
    return null;
  }

  // Поиск книг по id жанра
  @GetMapping("/search/byGenreId")
  public ResponseEntity<List<BookDto>> searchBooksByGenre(@RequestParam Long genreId) {
    return null;
  }

  // Получение обложки книги по id
  @GetMapping("/{bookId}/image")
  public ResponseEntity<byte[]> getBookCover(@PathVariable Long bookId) {
    byte[] coverBytes = bookService.getBookCover(bookId);
    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(coverBytes);
  }
}