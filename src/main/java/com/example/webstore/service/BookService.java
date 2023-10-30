package com.example.webstore.service;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.entity.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

  Page<BookDto> getAllBooks(
      String title,
      Long authorId,
      Long genreId,
      Double minPrice,
      Double maxPrice,
      boolean ascendingPrice,
      boolean descendingPrice,
      boolean ascendingPublicationYear,
      Pageable pageable);

  Book getBookById(Long id);

  Optional<Book> getBookByISBN(String isbn);
}