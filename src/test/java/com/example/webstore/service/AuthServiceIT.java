package com.example.webstore.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.webstore.IntegrationTestBase;
import com.example.webstore.exception.DuplicateException;
import com.example.webstore.model.dto.UserDtoRegister;
import com.example.webstore.model.entity.User;
import com.example.webstore.model.enums.Role;
import com.example.webstore.repository.UserRepository;
import com.example.webstore.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
class AuthServiceIT extends IntegrationTestBase {

  private static final String USERNAME = "username";
  private static final String EMAIL = "email";
  private static final String NEW_USERNAME = "newUsername";
  private static final String NEW_EMAIL = "newEmail";
  private static final String PASSWORD = "password";
  private static final String PASSWORD_SUCCESSFUL = "";

  private final AuthServiceImpl authService;
  private final UserRepository userRepository;

  /**
   * Тестирование регистрации пользователя с уже существующим именем пользователя. Ожидается, что
   * при попытке регистрации пользователя с уже существующим именем, будет выброшено исключение
   * DuplicateException.
   */
  @Test
  void registerUserWithDuplicateUsername() {
    User user = User.builder()
        .username(USERNAME)
        .email(EMAIL)
        .password(PASSWORD)
        .role(Role.USER)
        .build();
    userRepository.save(user);

    assertThatThrownBy(() -> authService.register(UserDtoRegister.builder()
        .username(USERNAME)
        .email(NEW_EMAIL)
        .password(PASSWORD)
        .build()))
        .isInstanceOf(DuplicateException.class);

  }

  /**
   * Тестирование регистрации пользователя с уже существующим адресом электронной почты. Ожидается,
   * что при попытке регистрации пользователя с уже существующим адресом почты, будет выброшено
   * исключение DuplicateException.
   */
  @Test
  void registerUserWithDuplicateEmail() {
    User user = User.builder()
        .username(USERNAME)
        .email(EMAIL)
        .password(PASSWORD)
        .role(Role.USER)
        .build();
    userRepository.save(user);

    assertThatThrownBy(() -> authService.register(UserDtoRegister.builder()
        .username(NEW_USERNAME)
        .email(EMAIL)
        .password(PASSWORD)
        .build()))
        .isInstanceOf(DuplicateException.class);
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
        .password(PASSWORD_SUCCESSFUL)
        .build();

    UserDtoRegister result = authService.register(userDto);

    assertThat(result)
        .isNotNull()
        .isEqualTo(userDto);
  }
}