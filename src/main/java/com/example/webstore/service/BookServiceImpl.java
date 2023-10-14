package com.example.webstore.service;

import com.example.webstore.model.entity.Book;
import com.example.webstore.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  @Override
  @Transactional(readOnly = true)
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  @Override
  public Optional<Book> getBookById(Long id) {
    return bookRepository.findById(id);
  }
}
