package com.example.webstore.controller;

import com.example.webstore.model.dto.BookDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/favorites")
public class FavoriteController {

  // Получение списка избранных книг пользователя
  @GetMapping("/{bookId}")
  public ResponseEntity<List<BookDto>> getFavorites(@PathVariable Long bookId) {
    return null;
  }

  // Добавление книги в список избранных
  @PostMapping("/{bookId}")
  public ResponseEntity<BookDto> addToFavorites(
      @PathVariable Long bookId) {
    return null;
  }

  // Удаление книги из списка избранных
  @DeleteMapping("/{bookId}")
  public ResponseEntity<Void> removeFromFavorites(
      @PathVariable Long bookId) {
    return null;
  }
}