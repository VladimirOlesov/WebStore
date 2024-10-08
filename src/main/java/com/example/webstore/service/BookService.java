package com.example.webstore.service;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.entity.Book;
import com.example.webstore.model.enums.SortBy;
import com.example.webstore.model.enums.SortDirection;
import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {

  Page<BookDto> getBooks(
      String title,
      Long authorId,
      Long genreId,
      BigDecimal minPrice,
      BigDecimal maxPrice,
      SortBy sortBy,
      SortDirection sortDirection,
      Pageable pageable);

  BookDto getBookDtoById(Long bookId);

  Book getBookById(Long bookId);

  void deleteBookById(Long bookId);

  String saveBookCover(Long bookId, MultipartFile file);

  byte[] getBookCover(Long bookId);

  byte[] exportBooksToExcel();

  BookDto saveBook(BookDto bookDto);
}