package com.example.webstore.model.dto;

import lombok.Builder;

@Builder
public record FavoriteIdDto(
    Long userId,
    Long bookId) {

}