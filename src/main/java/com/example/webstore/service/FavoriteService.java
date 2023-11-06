package com.example.webstore.service;

import com.example.webstore.model.dto.BookDto;
import java.util.List;

public interface FavoriteService {

  void removeFromFavorites(Long favoriteId);

  void addToFavorites(Long bookId);

  List<BookDto> getFavoriteBooks();
}