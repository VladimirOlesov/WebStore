package com.example.webstore.model.mapper;

import com.example.webstore.model.entity.Genre;
import com.example.webstore.repository.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GenreRepositoryMapper {

  private final GenreRepository genreRepository;

  @CustomMapping
  public Genre mapToGenre(Long genreId) {
    return genreRepository.findById(genreId)
        .orElseThrow(() -> new EntityNotFoundException("Жанр не найден"));
  }
}