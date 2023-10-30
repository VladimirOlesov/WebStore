package com.example.webstore.controller;


import com.example.webstore.model.dto.BookDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/admin/book")
public class AdminBookController {

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

  // Обновление информации о книге по ее id
  @PutMapping("/{bookId}")
  public ResponseEntity<BookDto> updateBook(@PathVariable Long bookId,
      @RequestBody BookDto bookDto) {
    return null;
  }

  // Удаление книги по ее id
  @DeleteMapping("/{bookId}")
  public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
    return null;
  }

}