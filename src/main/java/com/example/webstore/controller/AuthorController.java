package com.example.webstore.controller;

import com.example.webstore.model.dto.AuthorDto;
import com.example.webstore.service.AuthorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authors")
public class AuthorController {

  private final AuthorService authorService;

  @GetMapping
  public ResponseEntity<List<AuthorDto>> gerAuthors(
      @RequestParam(name = "name", required = false) String authorName) {
    return ResponseEntity.ok(authorService.getAuthors(authorName));
  }
}