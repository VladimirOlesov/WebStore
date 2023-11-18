package com.example.webstore.service.impl;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.dto.FavoriteIdDto;
import com.example.webstore.model.entity.Book;
import com.example.webstore.model.entity.Favorite;
import com.example.webstore.model.entity.FavoriteId;
import com.example.webstore.model.entity.User;
import com.example.webstore.model.mapper.BookMapper;
import com.example.webstore.model.mapper.FavoriteMapper;
import com.example.webstore.repository.BookRepository;
import com.example.webstore.repository.FavoriteRepository;
import com.example.webstore.service.FavoriteService;
import com.example.webstore.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FavoriteServiceImpl implements FavoriteService {

  private final FavoriteRepository favoriteRepository;
  private final BookMapper bookMapper;
  private final UserService userService;
  private final BookRepository bookRepository;
  private final FavoriteMapper favoriteMapper;

  @Override
  @Transactional
  public void removeFromFavorites(Long bookId) {
    User user = userService.getAuthenticatedUser();

    Favorite favorite = favoriteRepository.findByUserIdAndBookId(user.getId(), bookId)
        .orElseThrow(() -> new EntityNotFoundException("Книга не найдена в избранном"));

    favoriteRepository.delete(favorite);
  }

  @Override
  @Transactional
  public FavoriteIdDto addToFavorites(Long bookId) {
    User user = userService.getAuthenticatedUser();

    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new EntityNotFoundException("Книга не найдена"));

    Favorite favorite = favoriteRepository.save(Favorite.builder()
        .id(new FavoriteId(user.getId(), book.getId()))
        .user(user)
        .book(book)
        .build());
    return favoriteMapper.favoriteIdToDto(favorite.getId());
  }

  @Override
  public List<BookDto> getFavoriteBooks() {
    User user = userService.getAuthenticatedUser();

    List<Favorite> favoriteBooks = favoriteRepository.findByUserId(user.getId());

    if (favoriteBooks.isEmpty()) {
      throw new EntityNotFoundException("В избранном нет книг");
    }

    return favoriteBooks.stream()
        .map(favorite -> bookMapper.bookToBookDto(favorite.getBook()))
        .collect(Collectors.toList());
  }
}