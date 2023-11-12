package com.example.webstore.service.impl;

import com.example.webstore.exception.BookCoverException;
import com.example.webstore.model.BookSpecifications;
import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.entity.Book;
import com.example.webstore.model.enums.SortBy;
import com.example.webstore.model.enums.SortDirection;
import com.example.webstore.model.mapper.BookMapper;
import com.example.webstore.repository.BookRepository;
import com.example.webstore.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
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
    Specification<Book> spec = Specification
        .where(BookSpecifications.titleContains(title))
        .and(BookSpecifications.authorIs(authorId))
        .and(BookSpecifications.genreIs(genreId))
        .and(BookSpecifications.priceBetween(minPrice, maxPrice))
        .and(BookSpecifications.notDeleted())
        .and(BookSpecifications.orderBy(sortBy, sortDirection));

    Page<Book> books = bookRepository.findAll(spec, pageable);

    if (books.isEmpty()) {
      throw new EntityNotFoundException("Книги не найдены");
    }

    return books.map(bookMapper::bookToBookDto);
  }

  @Override
  public BookDto getBookDtoById(Long bookId) {
    return bookMapper.bookToBookDto(getBookById(bookId));
  }

  @Override
  public Book getBookById(Long bookId) {
    return bookRepository.findByIdAndIsDeletedIsFalse(bookId)
        .orElseThrow(() -> new EntityNotFoundException("Книга не найдена"));
  }

  @Override
  @Transactional
  public void deleteBookById(Long bookId) {
    Book book = getBookById(bookId);
    book.setIsDeleted(true);
    bookRepository.save(book);
  }

  @Override
  @Transactional
  public String saveBookCover(Long bookId, MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new IllegalArgumentException("Файл не передан или пуст");
    }

    Book book = getBookById(bookId);

    String uniqueFilename = String.format("%s_book_%d.%s",
        Paths.get(uploadPath).getFileName(),
        bookId,
        FilenameUtils.getExtension(file.getOriginalFilename()));

    String filePath = Paths.get(uploadPath).resolve(uniqueFilename).toString();

    try {
      if (Files.notExists(Paths.get(uploadPath))) {
        Files.createDirectories(Paths.get(uploadPath));
      }
      Files.write(Paths.get(filePath), file.getBytes());
      book.setCoverPath(filePath);
      bookRepository.save(book);

      return filePath;

    } catch (IOException e) {

      throw new BookCoverException("Ошибка при сохранении файла обложки");
    }
  }

  @Override
  public byte[] getBookCover(Long bookId) {
    Book book = getBookById(bookId);

    Path coverPath = Path.of(book.getCoverPath());

    try {
      if (Files.exists(coverPath)) {
        return Files.readAllBytes(coverPath);
      } else {
        throw new FileNotFoundException("Обложка не найдена");
      }
    } catch (IOException e) {
      throw new BookCoverException("Ошибка при чтении файла обложки");
    }
  }
}