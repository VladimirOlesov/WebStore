package com.example.webstore.controller;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.entity.FavoriteId;
import com.example.webstore.service.FavoriteService;
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

  private final FavoriteService favoriteService;

  // Получение списка избранных книг пользователя
  @GetMapping
  public ResponseEntity<List<BookDto>> getFavoriteBooks() {
    return ResponseEntity.ok(favoriteService.getFavoriteBooks());
  }

  // Добавление книги в список избранных
  @PostMapping("/{bookId}")
  public ResponseEntity<FavoriteId> addToFavorites(@PathVariable Long bookId) {
    return ResponseEntity.ok(favoriteService.addToFavorites(bookId));
  }

  // Удаление книги из списка избранных
  @DeleteMapping("/{bookId}")
  public ResponseEntity<Void> removeFromFavorites(@PathVariable Long bookId) {
    favoriteService.removeFromFavorites(bookId);
    return ResponseEntity.ok().build();
  }
}