package com.example.webstore.controller;

import com.example.webstore.model.dto.UserDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/users")
public class AdminUserController {

  // Получение списка всех пользователей
  @GetMapping
  public ResponseEntity<List<UserDto>> getAllUsers() {
    return null;
  }

  // Удаление пользователя
  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    return null;
  }
}