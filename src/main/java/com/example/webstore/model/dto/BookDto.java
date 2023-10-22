package com.example.webstore.model.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record BookDto(
    String title,
    @NotNull AuthorDto author,
    @NotNull GenreDto genre,
    Integer publicationYear,
    BigDecimal price,
    String ISBN,
    Integer pageCount,
    Integer ageRating) {

}