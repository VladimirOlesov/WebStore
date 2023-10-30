package com.example.webstore.model.dto;

import lombok.Builder;

@Builder
public record ErrorResponseDto(String message) {

}