package com.example.webstore.service;

import com.example.webstore.model.entity.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {

  List<Book> getAllBooks();

  Optional<Book> getBookById(Long id);
}
