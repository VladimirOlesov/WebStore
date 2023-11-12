package com.example.webstore.controller;

import com.example.webstore.model.dto.GenreDto;
import com.example.webstore.service.GenreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/genres")
public class GenreController {

  private final GenreService genreService;

  @GetMapping
  public ResponseEntity<List<GenreDto>> gerAuthors(
      @RequestParam(name = "name", required = false) String genreName) {
    return ResponseEntity.ok(genreService.getGenres(genreName));
  }
}