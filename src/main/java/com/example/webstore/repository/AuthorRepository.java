package com.example.webstore.repository;

import com.example.webstore.model.entity.Author;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

  List<Author> findAllByAuthorNameContainingIgnoreCaseOrderByAuthorNameAsc(String authorName);
}