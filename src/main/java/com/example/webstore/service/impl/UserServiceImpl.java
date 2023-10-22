package com.example.webstore.service.impl;

import com.example.webstore.exception.EntityNotFoundException;
import com.example.webstore.model.dto.UserDtoRegister;
import com.example.webstore.model.entity.User;
import com.example.webstore.model.mapper.UserMapper;
import com.example.webstore.repository.UserRepository;
import com.example.webstore.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public Optional<User> getUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public boolean existsUserByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public boolean existsUserByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public UserDtoRegister save(UserDtoRegister userDtoRegister) {
    User user = userMapper.userDtoToUser(userDtoRegister);
    userRepository.save(user);
    return userMapper.convertToDto(user);
  }

  @Override
  public UserDetailsService userDetailsService() {
    return username -> userRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
  }
}