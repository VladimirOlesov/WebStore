package com.example.webstore.service.impl;

import com.example.webstore.model.dto.UserDtoRegister;
import com.example.webstore.model.entity.User;
import com.example.webstore.model.enums.Role;
import com.example.webstore.model.mapper.UserMapper;
import com.example.webstore.repository.UserRepository;
import com.example.webstore.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  public User getUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
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
  @Transactional
  public UserDtoRegister save(UserDtoRegister userDtoRegister) {
    var user = userMapper.userDtoToUser(userDtoRegister);
    user.setRole(Role.USER);
    userRepository.save(user);
    return userMapper.convertToDto(user);
  }

  @Override
  public UserDetailsService userDetailsService() {
    return username -> userRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
  }
}