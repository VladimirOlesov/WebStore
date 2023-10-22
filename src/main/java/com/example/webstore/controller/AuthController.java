package com.example.webstore.controller;

import com.example.webstore.model.dto.UserDtoLogin;
import com.example.webstore.model.dto.UserDtoRegister;
import com.example.webstore.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<UserDtoRegister> register(@Valid @RequestBody UserDtoRegister userDto) {
    return ResponseEntity.ok(authService.register(userDto));
  }

  @PostMapping("/login")
  public ResponseEntity<String> login(@Valid @RequestBody UserDtoLogin userDto) {
    return ResponseEntity.ok(authService.authenticate(userDto));
  }
}