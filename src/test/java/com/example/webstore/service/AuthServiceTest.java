package com.example.webstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.example.webstore.exception.DuplicateException;
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

  /**
   * Тестирование регистрации пользователя с уже существующим именем пользователя. Ожидается, что
   * при попытке регистрации пользователя с уже существующим именем, будет выброшено исключение
   * DuplicateException.
   */
  @Test
  void registerUserWithDuplicateUsername() {

    doReturn(true).when(userRepository).existsByUsername(EXISTING_USERNAME);

    assertThatThrownBy(() -> authService.register(UserDtoRegister.builder()
        .username(EXISTING_USERNAME)
        .email(NEW_EMAIL)
        .password(PASSWORD)
        .build()))
        .isInstanceOf(DuplicateException.class);

    verify(userRepository).existsByUsername(EXISTING_USERNAME);
  }

  /**
   * Тестирование регистрации пользователя с уже существующим адресом электронной почты. Ожидается,
   * что при попытке регистрации пользователя с уже существующим адресом почты, будет выброшено
   * исключение DuplicateException.
   */
  @Test
  void registerUserWithDuplicateEmail() {

    doReturn(false).when(userRepository).existsByUsername(NEW_USERNAME);
    doReturn(true).when(userRepository).existsByEmail(EXISTING_EMAIL);

    assertThatThrownBy(() -> authService.register(UserDtoRegister.builder()
        .username(NEW_USERNAME)
        .email(EXISTING_EMAIL)
        .password(PASSWORD)
        .build()))
        .isInstanceOf(DuplicateException.class);

    verify(userRepository).existsByUsername(NEW_USERNAME);
    verify(userRepository).existsByEmail(EXISTING_EMAIL);
  }

  /**
   * Тестирование успешной регистрации пользователя. Ожидается, что при передаче корректных данных
   * для нового пользователя, метод регистрации вернет объект пользователя, и этот объект будет
   * соответствовать переданным данным.
   */
  @Test
  void registerUserSuccessfully() {

    UserDtoRegister userDto = UserDtoRegister.builder()
        .username(NEW_USERNAME)
        .email(NEW_EMAIL)
        .password(PASSWORD)
        .build();

    doReturn(false).when(userRepository).existsByUsername(NEW_USERNAME);
    doReturn(false).when(userRepository).existsByEmail(NEW_EMAIL);
    doReturn(userDto).when(userService).save(userDto);

    UserDtoRegister result = authService.register(userDto);

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