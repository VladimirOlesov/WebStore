package com.example.webstore.service.impl;

import com.example.webstore.model.BookSpecifications;
import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.entity.Book;
import com.example.webstore.model.enums.SortDirection;
import com.example.webstore.model.mapper.BookMapper;
import com.example.webstore.repository.BookRepository;
import com.example.webstore.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  @Override
  public Page<BookDto> getAllBooks(
      String title,
      Long authorId,
      Long genreId,
      Double minPrice,
      Double maxPrice,
      boolean ascendingPrice,
      boolean descendingPrice,
      boolean ascendingPublicationYear,
      Pageable pageable) {
    Specification<Book> spec = Specification
        .where(BookSpecifications.titleContains(title))
        .and(BookSpecifications.authorIs(authorId))
        .and(BookSpecifications.genreIs(genreId))
        .and(BookSpecifications.priceBetween(minPrice, maxPrice));

    if (ascendingPrice) {
      spec = spec.and(BookSpecifications.orderByPrice(SortDirection.ASC));
    } else if (descendingPrice) {
      spec = spec.and(BookSpecifications.orderByPrice(SortDirection.DESC));
    }

    if (ascendingPublicationYear) {
      spec = spec.and(BookSpecifications.orderByPublicationYear(SortDirection.ASC));
    }

    Page<Book> books = bookRepository.findAll(spec, pageable);

    if (books.isEmpty()) {
      throw new EntityNotFoundException("Книги не найдены");
    }

    return books.map(bookMapper::bookToBookDto);
  }

  @Override
  public Book getBookById(Long bookId) {
    return bookRepository.findById(bookId)
        .orElseThrow(() -> new EntityNotFoundException("Книга не найдена"));
  }

  @Override
  public Optional<Book> getBookByISBN(String isbn) {
    return bookRepository.findByISBN(isbn);
  }
}