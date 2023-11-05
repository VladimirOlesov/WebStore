package com.example.webstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.example.webstore.exception.DuplicateUserException;
import com.example.webstore.model.dto.UserDtoRegister;
import com.example.webstore.repository.UserRepository;
import com.example.webstore.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  private static final String EXISTING_USERNAME = "existingUsername";
  private static final String EXISTING_EMAIL = "existingEmail";
  private static final String PASSWORD = "password";
  private static final String NEW_USERNAME = "newUsername";
  private static final String NEW_EMAIL = "newEmail";

  @Mock
  private UserRepository userRepository;

  @Mock
  private UserService userService;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtService jwtService;

  @InjectMocks
  private AuthServiceImpl authService;

  @Test
  void registerUserWithDuplicateUsername() {

    doReturn(true).when(userRepository).existsByUsername(EXISTING_USERNAME);

    assertThatThrownBy(() -> authService.register(UserDtoRegister.builder()
        .username(EXISTING_USERNAME)
        .email(NEW_EMAIL)
        .password(PASSWORD)
        .build()))
        .isInstanceOf(DuplicateUserException.class);

    verify(userRepository).existsByUsername(EXISTING_USERNAME);
  }

  @Test
  void registerUserWithDuplicateEmail() {

    doReturn(false).when(userRepository).existsByUsername(NEW_USERNAME);
    doReturn(true).when(userRepository).existsByEmail(EXISTING_EMAIL);

    assertThatThrownBy(() -> authService.register(UserDtoRegister.builder()
        .username(NEW_USERNAME)
        .email(EXISTING_EMAIL)
        .password(PASSWORD)
        .build()))
        .isInstanceOf(DuplicateUserException.class);

    verify(userRepository).existsByUsername(NEW_USERNAME);
    verify(userRepository).existsByEmail(EXISTING_EMAIL);
  }

  @Test
  void registerUserSuccessfully() {

    var userDto = UserDtoRegister.builder()
        .username(NEW_USERNAME)
        .email(NEW_EMAIL)
        .password(PASSWORD)
        .build();

    doReturn(false).when(userRepository).existsByUsername(NEW_USERNAME);
    doReturn(false).when(userRepository).existsByEmail(NEW_EMAIL);
    doReturn(userDto).when(userService).save(userDto);

    var result = authService.register(userDto);

    assertThat(result).isNotNull();
    assertThat(result).isEqualTo(userDto);

    verify(userRepository).existsByUsername(NEW_USERNAME);
    verify(userRepository).existsByEmail(NEW_EMAIL);
    verify(userService).save(userDto);
  }

  @AfterEach
  void verifyInteractions() {

    verifyNoMoreInteractions(userRepository, userService, authenticationManager, jwtService);
  }
}