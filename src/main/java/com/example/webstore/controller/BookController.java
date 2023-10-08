package com.example.webstore.controller;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.converter.BookConverter;
import com.example.webstore.model.entity.Book;
import com.example.webstore.service.BookService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

  private final BookService bookService;

  @GetMapping
  public ResponseEntity<List<BookDto>> getBooks() {
    List<Book> books = bookService.getAllBooks();
    if (books.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT)
          .body(Collections.emptyList());
    }

    List<BookDto> booksDto = books.stream()
        .map(BookConverter::convertToDto)
        .collect(Collectors.toList());

    return new ResponseEntity<>(booksDto, HttpStatus.OK);
  }
}