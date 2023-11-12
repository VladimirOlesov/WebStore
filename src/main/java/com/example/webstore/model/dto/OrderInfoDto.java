package com.example.webstore.model.dto;

import lombok.Builder;

@Builder
public record OrderInfoDto(
    Long orderId,
    Long userId,
    Long bookId) {

}