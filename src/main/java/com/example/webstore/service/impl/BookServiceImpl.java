package com.example.webstore.service.impl;

import com.example.webstore.exception.EntityNotFoundException;
import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.entity.Book;
import com.example.webstore.model.mapper.BookMapper;
import com.example.webstore.repository.BookRepository;
import com.example.webstore.service.BookService;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  @Override
  public List<BookDto> getAllBooks() {
    List<Book> books = bookRepository.findAll();
    if (books.isEmpty()) {
      throw new EntityNotFoundException("Книги не найдены");
    }

    return books.stream()
        .map(bookMapper::bookToBookDto)
        .toList();
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