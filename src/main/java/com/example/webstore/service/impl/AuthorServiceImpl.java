package com.example.webstore.service.impl;

import com.example.webstore.model.dto.AuthorDto;
import com.example.webstore.model.entity.Author;
import com.example.webstore.model.entity.Author_;
import com.example.webstore.model.mapper.AuthorMapper;
import com.example.webstore.repository.AuthorRepository;
import com.example.webstore.service.AuthorService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;
  private final AuthorMapper authorMapper;

  @Override
  public List<AuthorDto> getAuthors(String authorName) {
    List<Author> authors;
    if (authorName != null && !authorName.isEmpty()) {
      authors = authorRepository.findAllByAuthorNameContainingIgnoreCaseOrderByAuthorNameAsc(
          authorName);
    } else {
      authors = authorRepository.findAll(Sort.by(Sort.Direction.ASC, Author_.AUTHOR_NAME));
    }

    if (authors.isEmpty()) {
      throw new EntityNotFoundException("Жанры не найдены");
    }

    return authors.stream()
        .map(authorMapper::authorToDto)
        .collect(Collectors.toList());
  }
}