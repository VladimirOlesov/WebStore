package com.example.webstore.service;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.dto.FavoriteIdDto;
import java.util.List;

public interface FavoriteService {

  void removeFromFavorites(Long favoriteId);

  FavoriteIdDto addToFavorites(Long bookId);

  List<BookDto> getFavoriteBooks();
}