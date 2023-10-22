package com.example.webstore.service;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.entity.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {

  List<BookDto> getAllBooks();

  Book getBookById(Long id);

  Optional<Book> getBookByISBN(String isbn);
}