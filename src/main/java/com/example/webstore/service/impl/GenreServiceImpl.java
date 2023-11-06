package com.example.webstore.service.impl;

import com.example.webstore.model.dto.GenreDto;
import com.example.webstore.model.entity.Genre;
import com.example.webstore.model.mapper.GenreMapper;
import com.example.webstore.repository.GenreRepository;
import com.example.webstore.service.GenreService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

  private final GenreRepository genreRepository;
  private final GenreMapper genreMapper;

  @Override
  public List<GenreDto> getGenres(String name) {
    var allGenres = genreRepository.findAll();

    if (name != null && !name.isEmpty()) {
      allGenres = allGenres.stream()
          .filter(author -> author.getGenreName().toLowerCase().contains(name.toLowerCase()))
          .sorted(Comparator.comparing(Genre::getGenreName, String.CASE_INSENSITIVE_ORDER))
          .collect(Collectors.toList());
    }

    if (allGenres.isEmpty()) {
      throw new EntityNotFoundException("Жанры не найдены");
    }

    return allGenres.stream()
        .map(genreMapper::genreToDto)
        .collect(Collectors.toList());
  }
}