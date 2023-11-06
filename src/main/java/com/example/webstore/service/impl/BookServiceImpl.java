package com.example.webstore.service.impl;

import com.example.webstore.exception.BookCoverStorageException;
import com.example.webstore.model.BookSpecifications;
import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.entity.Book;
import com.example.webstore.model.enums.SortBy;
import com.example.webstore.model.enums.SortDirection;
import com.example.webstore.model.mapper.BookMapper;
import com.example.webstore.repository.BookRepository;
import com.example.webstore.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  @Value("${book.covers.upload.path}")
  private String uploadPath;

  @Override
  public Page<BookDto> getBooks(
      String title,
      Long authorId,
      Long genreId,
      BigDecimal minPrice,
      BigDecimal maxPrice,
      SortBy sortBy,
      SortDirection sortDirection,
      Pageable pageable) {
    var spec = Specification
        .where(BookSpecifications.titleContains(title))
        .and(BookSpecifications.authorIs(authorId))
        .and(BookSpecifications.genreIs(genreId))
        .and(BookSpecifications.priceBetween(minPrice, maxPrice))
        .and(BookSpecifications.orderBy(sortBy, sortDirection));

    var books = bookRepository.findAll(spec, pageable);

    if (books.isEmpty()) {
      throw new EntityNotFoundException("Книги не найдены");
    }

    return books.map(bookMapper::bookToBookDto);
  }

  @Override
  public BookDto getBookById(Long bookId) {
    var book = bookRepository.findById(bookId)
        .orElseThrow(() -> new EntityNotFoundException("Книга не найдена"));
    return bookMapper.bookToBookDto(book);
  }

  @Override
  public Book getBookByISBN(String isbn) {
    return bookRepository.findByISBN(isbn)
        .orElseThrow(() -> new EntityNotFoundException("Книга не найдена"));
  }

  @Override
  @Transactional
  public void deleteBookById(Long bookId) {
    bookRepository.delete(bookRepository.findById(bookId)
        .orElseThrow(() -> new EntityNotFoundException("Книга не найдена")));
  }

  @Override
  @Transactional
  public String saveBookCover(Long bookId, MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new IllegalArgumentException("Файл не передан или пуст");
    }

    var book = bookRepository.findById(bookId)
        .orElseThrow(() -> new EntityNotFoundException("Книга не найдена"));

    var filePath = Paths.get(uploadPath, file.getOriginalFilename()).toString();

    try {
      Files.createDirectories(Paths.get(uploadPath));

      Files.write(Paths.get(filePath), file.getBytes());
      book.setCoverPath(filePath);
      bookRepository.save(book);

      return filePath;

    } catch (IOException e) {
      if (Files.notExists(Paths.get(uploadPath))) {
        throw new BookCoverStorageException("Ошибка при создании папки для сохранения обложки");
      } else {
        throw new BookCoverStorageException("Ошибка при сохранении файла обложки");
      }
    }
  }
}