package com.example.webstore.service;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.entity.FavoriteId;
import java.util.List;

public interface FavoriteService {

  void removeFromFavorites(Long favoriteId);

  FavoriteId addToFavorites(Long bookId);

  List<BookDto> getFavoriteBooks();
}