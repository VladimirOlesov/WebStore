package com.example.webstore.controller;

import static com.example.webstore.model.dto.AuthorDto.Fields.authorName;
import static com.example.webstore.model.dto.GenreDto.Fields.genreName;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.webstore.IntegrationTestBase;

import com.example.webstore.model.dto.AuthorDto;
import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.dto.GenreDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = "testUser", authorities = {"USER", "ADMIN"})
class AdminBookControllerIT extends IntegrationTestBase {

  private final MockMvc mockMvc;

  private final ObjectMapper objectMapper;

  @Test
  void createBook() throws Exception {

    BookDto bookDto = BookDto.builder()
        .title("title")
        .author(AuthorDto.builder().authorName(authorName).build())
        .genre(GenreDto.builder().genreName(genreName).build())
        .publicationYear(2000)
        .price(new BigDecimal("500.00"))
        .ISBN("ISBN")
        .pageCount(500)
        .ageRating(16)
        .coverPath("coverPath")
        .deleted(false)
        .build();

    mockMvc.perform(post("/admin/books")

            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(bookDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title", is(bookDto.title())))
        .andExpect(jsonPath("$.author.authorName", is(bookDto.author().authorName())))
        .andExpect(jsonPath("$.genre.genreName", is(bookDto.genre().genreName())))
        .andExpect(jsonPath("$.publicationYear", is(bookDto.publicationYear())))
        .andExpect(jsonPath("$.price", is(bookDto.price().doubleValue())))
        .andExpect(jsonPath("$.ISBN", is(bookDto.ISBN())))
        .andExpect(jsonPath("$.pageCount", is(bookDto.pageCount())))
        .andExpect(jsonPath("$.ageRating", is(bookDto.ageRating())))
        .andExpect(jsonPath("$.coverPath", is(bookDto.coverPath())))
        .andExpect(jsonPath("$.deleted", is(bookDto.deleted())));
  }
}