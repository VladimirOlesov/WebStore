package com.example.webstore.service;

import static com.example.webstore.model.entity.User.Fields.email;
import static com.example.webstore.model.entity.User.Fields.password;
import static com.example.webstore.model.entity.User.Fields.username;
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

  private static final String NEW_USERNAME = "newUsername";
  private static final String NEW_EMAIL = "newEmail";
  private static final String PASSWORD_SUCCESSFUL = "";

  private final AuthServiceImpl authService;
  private final UserRepository userRepository;

  private void createTestUser() {
    userRepository.save(
        User.builder()
            .username(username)
            .email(email)
            .password(password)
            .role(Role.USER)
            .build());
  }

  /**
   * Тестирование регистрации пользователя с уже существующим именем пользователя. Ожидается, что
   * при попытке регистрации пользователя с уже существующим именем, будет выброшено исключение
   * DuplicateException.
   */
  @Test
  void registerUserWithDuplicateUsername() {
    createTestUser();

    assertThatThrownBy(() -> authService.register(UserDtoRegister.builder()
        .username(username)
        .email(NEW_EMAIL)
        .password(password)
        .build()))
        .isInstanceOf(DuplicateException.class)
        .hasMessage("Пользователь c таким именем или электронной почтой уже существует");

  }

  /**
   * Тестирование регистрации пользователя с уже существующим адресом электронной почты. Ожидается,
   * что при попытке регистрации пользователя с уже существующим адресом почты, будет выброшено
   * исключение DuplicateException.
   */
  @Test
  void registerUserWithDuplicateEmail() {
    createTestUser();

    assertThatThrownBy(() -> authService.register(UserDtoRegister.builder()
        .username(NEW_USERNAME)
        .email(email)
        .password(password)
        .build()))
        .isInstanceOf(DuplicateException.class)
        .hasMessage("Пользователь c таким именем или электронной почтой уже существует");
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