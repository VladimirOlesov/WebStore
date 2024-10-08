package com.example.webstore.controller;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/books")
public class AdminBookController {

  private final BookService bookService;

  // Сохранение книги
  @PostMapping
  public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
    return ResponseEntity.ok(bookService.saveBook(bookDto));
  }

  // Загрузка изображения книги
  @PostMapping("/{bookId}/image")
  public ResponseEntity<String> uploadImage(@PathVariable Long bookId,
      @RequestParam("file") MultipartFile file) {
    return ResponseEntity.ok(bookService.saveBookCover(bookId, file));
  }

  // Обновление информации о книге по ее id
  @PutMapping("/{bookId}")
  public ResponseEntity<BookDto> updateBook(@PathVariable Long bookId,
      @RequestBody BookDto bookDto) {
    return null;
  }

  // Удаление книги по ее id
  @DeleteMapping("/{bookId}")
  public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
    bookService.deleteBookById(bookId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/to-excel")
  public ResponseEntity<byte[]> export() {
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=books.xlsx")
        .body(bookService.exportBooksToExcel());
  }
}