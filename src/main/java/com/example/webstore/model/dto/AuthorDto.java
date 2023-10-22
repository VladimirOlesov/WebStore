package com.example.webstore.model.dto;

import lombok.Builder;

@Builder
public record AuthorDto(
    Long authorId,
    String authorName) {

}