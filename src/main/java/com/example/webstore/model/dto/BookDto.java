package com.example.webstore.model.dto;

import java.math.BigDecimal;

public record BookDto(
    String title,
    String authorName,
    String genreName,
    Integer publicationYear,
    BigDecimal price,
    String ISBN,
    Integer pageCount,
    Integer ageRating) {

}