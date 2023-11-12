package com.example.webstore.service.impl;

import com.example.webstore.model.dto.GenreDto;
import com.example.webstore.model.entity.Genre;
import com.example.webstore.model.entity.Genre_;
import com.example.webstore.model.mapper.GenreMapper;
import com.example.webstore.repository.GenreRepository;
import com.example.webstore.service.GenreService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

  private final GenreRepository genreRepository;
  private final GenreMapper genreMapper;

  @Override
  public List<GenreDto> getGenres(String genreName) {
    List<Genre> genres;
    if (genreName != null && !genreName.isEmpty()) {
      genres = genreRepository.findAllByGenreNameContainingIgnoreCaseOrderByGenreNameAsc(genreName);
    } else {
      genres = genreRepository.findAll(Sort.by(Sort.Direction.ASC, Genre_.GENRE_NAME));
    }

    if (genres.isEmpty()) {
      throw new EntityNotFoundException("Жанры не найдены");
    }

    return genres.stream()
        .map(genreMapper::genreToDto)
        .collect(Collectors.toList());
  }
}