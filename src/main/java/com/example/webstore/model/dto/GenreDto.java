package com.example.webstore.model.dto;

import lombok.Builder;
import lombok.experimental.FieldNameConstants;

@Builder
@FieldNameConstants
public record GenreDto(
    Long genreId,
    String genreName) {

}