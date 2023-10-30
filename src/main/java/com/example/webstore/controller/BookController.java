package com.example.webstore.controller;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.mapper.BookMapper;
import com.example.webstore.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
  private final BookMapper bookMapper;

  // Получение списка книг по названию, с фильтрацией, сортировкой и пагинацией
  @GetMapping
  public ResponseEntity<Page<BookDto>> getBooks(
      @RequestParam(name = "title", required = false) String title,
      @RequestParam(name = "author", required = false) Long authorId,
      @RequestParam(name = "genre", required = false) Long genreId,
      @RequestParam(name = "minPrice", required = false) Double minPrice,
      @RequestParam(name = "maxPrice", required = false) Double maxPrice,
      @RequestParam(
          name = "ascendingPrice",
          required = false,
          defaultValue = "false") boolean ascendingPrice,
      @RequestParam(
          name = "descendingPrice",
          required = false,
          defaultValue = "false") boolean descendingPrice,
      @RequestParam(
          name = "ascendingPublicationYear",
          required = false,
          defaultValue = "ASC") boolean ascendingPublicationYear,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "1") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<BookDto> books = bookService.getAllBooks(title, authorId, genreId, minPrice, maxPrice,
        ascendingPrice, descendingPrice, ascendingPublicationYear, pageable);
    return ResponseEntity.ok(books);
  }

  // Получение книги по ее id
  @GetMapping("/{bookId}")
  public ResponseEntity<BookDto> getBookById(@PathVariable Long bookId) {
    BookDto bookDto = bookMapper.bookToBookDto(bookService.getBookById(bookId));
    return ResponseEntity.ok(bookDto);
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
}