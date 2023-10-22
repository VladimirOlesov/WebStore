package com.example.webstore.repository;

import com.example.webstore.model.entity.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

  Optional<Book> findByISBN(String isbn);

}