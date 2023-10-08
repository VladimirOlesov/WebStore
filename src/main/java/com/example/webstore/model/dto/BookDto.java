package com.example.webstore.model.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Builder
public record BookDto(
    String title,
    String authorName,
    String genreName,
    Integer publicationYear,
    BigDecimal price,
    String ISBN,
    Integer pageCount,
    Integer ageRating
) {}
