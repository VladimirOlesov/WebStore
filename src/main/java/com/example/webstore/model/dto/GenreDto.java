package com.example.webstore.model.dto;

import lombok.Builder;

@Builder
public record GenreDto(
    Long genreId,
    String genreName) {

}