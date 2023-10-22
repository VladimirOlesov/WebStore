package com.example.webstore.controller;

import com.example.webstore.model.dto.UserDto;
import com.example.webstore.model.dto.UserProfileDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

  // Получение списка всех пользователей
  @GetMapping
  public ResponseEntity<List<UserDto>> getAllUsers() {
    return null;
  }

  // Получение информации о пользователе по его id
  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
    return null;
  }

  // Удаление пользователя
  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    return null;
  }

  // Обновление профиля пользователя
  @PostMapping
  public ResponseEntity<UserProfileDto> updateProfile(@RequestBody UserProfileDto userProfileDto) {
    return null;
  }

  // Выход пользователя (деаутентификация)
  @PostMapping("/logout")
  public ResponseEntity<Void> logout() {
    return null;
  }

  // Загрузка изображения для профиля
  @PostMapping("/upload-image")
  public ResponseEntity<Void> uploadProfileImage(@RequestParam("file") MultipartFile file) {
    return null;
  }

  // Восстановление пароля
  @PostMapping("/password/reset")
  public ResponseEntity<Void> resetPassword(@RequestParam String email) {
    return null;
  }

  // Смена пароля
  @PostMapping("/password/change")
  public ResponseEntity<Void> changePassword(@RequestParam String newPassword) {
    return null;
  }
}