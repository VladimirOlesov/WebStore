package com.example.webstore.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Component
public class PasswordEncoderMapper {

  private final PasswordEncoder passwordEncoder;

  @EncodedMapping
  public String encodePassword(String password) {
    return passwordEncoder.encode(password);
  }
}