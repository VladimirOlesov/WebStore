package com.example.webstore.repository;

import com.example.webstore.model.entity.Genre;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

  List<Genre> findAllByGenreNameContainingIgnoreCaseOrderByGenreNameAsc(String genreName);
}