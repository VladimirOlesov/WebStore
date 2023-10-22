package com.example.webstore.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserDtoLogin(
    @NotBlank String username,
    @NotBlank String password) {

}