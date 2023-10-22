package com.example.webstore.controller;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.mapper.BookMapper;
import com.example.webstore.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

  private final BookService bookService;
  private final BookMapper bookMapper;

  // Получение списка всех книг, фильтрация по автору, жанру с сортировкой по имени
  @GetMapping
  public ResponseEntity<List<BookDto>> getBooks(
      @RequestParam(required = false) String sortTitle,
      @RequestParam(required = false) String sortDirection,
      @RequestParam(required = false) String author,
      @RequestParam(required = false) String genre) {
    return ResponseEntity.ok(bookService.getAllBooks());
  }

  // Получение дополнительных книг для пагинации и бесконечной загрузки
  @GetMapping("/load-more")
  public ResponseEntity<List<BookDto>> loadMoreBooks(
      @RequestParam int page,
      @RequestParam int pageSize) {
    return null;
  }

  // Получение книги по ее id
  @GetMapping("/{bookId}")
  public ResponseEntity<BookDto> getBookById(@PathVariable Long bookId) {
    BookDto bookDto = bookMapper.bookToBookDto(bookService.getBookById(bookId));
    return ResponseEntity.ok(bookDto);
  }

  // Создание новой книги
  @PostMapping
  public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
    return null;
  }

  // Загрузка изображения книги
  @PostMapping("/{bookId}/upload-image")
  public ResponseEntity<Void> uploadImage(@PathVariable Long bookId,
      @RequestParam("file") MultipartFile file) {
    return null;
  }

/*  @Lob
  @Column(name = "image")
  private byte[] image;*/

  // Обновление информации о книге по ее id
  @PostMapping("/{bookId}")
  public ResponseEntity<BookDto> updateBook(@PathVariable Long bookId,
      @RequestBody BookDto bookDto) {
    return null;
  }

  // Удаление книги по ее id
  @DeleteMapping("/{bookId}")
  public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
    return null;
  }

  // Поиск книги по названию
  @GetMapping("/search/byTitle")
  public ResponseEntity<BookDto> searchBookByTitle(@RequestParam String title) {
    return null;
  }

  // Поиск книг по автору
  @GetMapping("/search/byAuthor")
  public ResponseEntity<List<BookDto>> searchBooksByAuthor(@RequestParam String author) {
    return null;
  }

  // Поиск книг по жанру
  @GetMapping("/search/byGenre")
  public ResponseEntity<List<BookDto>> searchBooksByGenre(@RequestParam String genre) {
    return null;
  }
}