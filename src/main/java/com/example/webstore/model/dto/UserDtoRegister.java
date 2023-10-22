package com.example.webstore.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserDtoRegister(
    @Pattern(regexp = "^[a-zA-Z0-9_]+$",
        message = "Имя пользователя может содержать только латинские буквы, "
            + "арабские цифры и нижнее подчеркивание")
    @NotBlank @Size(min = 6, max = 20,
        message = "Длина имени пользователя от 6 до 20 символов") String username,
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).+$",
        message = "Пароль должен содержать верхний и нижний регистр, хотя бы одну цифру и символ")
    @NotBlank @Size(min = 6, max = 20,
        message = "Длина пароля от 6 до 20 символов") String password,
    @NotBlank @Email String email
) {

}