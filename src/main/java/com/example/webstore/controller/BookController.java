package com.example.webstore.controller;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.entity.Book;
import com.example.webstore.model.mapper.BookMapper;
import com.example.webstore.service.BookService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

  private final BookService bookService;
  private final BookMapper bookMapper;

  @GetMapping
  public ResponseEntity<List<BookDto>> getBooks() {
    List<Book> books = bookService.getAllBooks();
    if (books.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT)
          .body(Collections.emptyList());
    }

    List<BookDto> booksDto = books.stream()
        .map(bookMapper::bookToBookDto)
        .collect(Collectors.toList());

    return ResponseEntity.ok(booksDto);
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
    Optional<Book> optionalBook = bookService.getBookById(id);

    if (optionalBook.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Book book = optionalBook.get();
    BookDto bookDto = bookMapper.bookToBookDto(book);

    return ResponseEntity.ok(bookDto);
  }
}