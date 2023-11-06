package com.example.webstore.service.impl;

import com.example.webstore.model.dto.AuthorDto;
import com.example.webstore.model.entity.Author;
import com.example.webstore.model.mapper.AuthorMapper;
import com.example.webstore.repository.AuthorRepository;
import com.example.webstore.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;
  private final AuthorMapper authorMapper;

  @Override
  public List<AuthorDto> getAuthors(String name) {
    var allAuthors = authorRepository.findAll();

    if (name != null && !name.isEmpty()) {
      allAuthors = allAuthors.stream()
          .filter(author -> author.getAuthorName().toLowerCase().contains(name.toLowerCase()))
          .sorted(Comparator.comparing(Author::getAuthorName, String.CASE_INSENSITIVE_ORDER))
          .collect(Collectors.toList());
    }

    if (allAuthors.isEmpty()) {
      throw new EntityNotFoundException("Авторы не найдены");
    }

    return allAuthors.stream()
        .map(authorMapper::authorToDto)
        .collect(Collectors.toList());
  }
}