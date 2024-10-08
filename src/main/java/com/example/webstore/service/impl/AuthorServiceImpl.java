package com.example.webstore.service.impl;

import com.example.webstore.model.dto.AuthorDto;
import com.example.webstore.model.entity.Author;
import com.example.webstore.model.entity.Author_;
import com.example.webstore.model.enums.SortDirection;
import com.example.webstore.model.mapper.AuthorMapper;
import com.example.webstore.service.AuthorService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

  private final AuthorMapper authorMapper;

  private final EntityManager entityManager;

  @Override
  public List<AuthorDto> getAuthors(String authorName, SortDirection sortDirection) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Author> cq = cb.createQuery(Author.class);

    Root<Author> author = cq.from(Author.class);
    List<Predicate> predicates = new ArrayList<>();

    if (StringUtils.hasText(authorName)) {
      predicates.add(cb.like(cb.lower(author.get(Author_.authorName)),
          MessageFormat.format("%{0}%", authorName.toLowerCase())));
    }

    cq.where(predicates.toArray(Predicate[]::new));

    cq.orderBy(sortDirection == SortDirection.DESC ?
        cb.desc(author.get(Author_.authorName)) :
        cb.asc(author.get(Author_.authorName)));

    TypedQuery<Author> query = entityManager.createQuery(cq);
    List<Author> authors = query.getResultList();

    if (authors.isEmpty()) {
      throw new EntityNotFoundException("Авторы не найдены");
    }

    return authors.stream()
        .map(authorMapper::authorToDto)
        .collect(Collectors.toList());
  }
}