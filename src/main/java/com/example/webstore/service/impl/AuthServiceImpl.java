package com.example.webstore.service.impl;

import com.example.webstore.exception.DuplicateUserException;
import com.example.webstore.model.dto.UserDtoRegister;
import com.example.webstore.repository.UserRepository;
import com.example.webstore.service.JwtService;
import com.example.webstore.model.dto.UserDtoLogin;
import com.example.webstore.service.AuthService;
import com.example.webstore.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;

  private final JwtService jwtService;

  private final UserService userService;

  private final UserRepository userRepository;

  @Override
  @Transactional
  public UserDtoRegister register(UserDtoRegister userDto) {

    if (userRepository.existsByUsername(
        userDto.username())
        || userRepository.existsByEmail(userDto.email())) {
      throw new DuplicateUserException(
          "Пользователь c таким именем или электронной почтой уже существует");
    }
    return userService.save(userDto);
  }

  @Override
  public String authenticate(UserDtoLogin userDtoLogin) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(userDtoLogin.username(),
            userDtoLogin.password()));

    var user = userRepository.findByUsername(userDtoLogin.username())
        .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
    return jwtService.generateToken(user);
  }
}