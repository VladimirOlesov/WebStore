package com.example.webstore.service;

import com.example.webstore.model.dto.UserDtoRegister;
import com.example.webstore.model.entity.User;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

  boolean existsUserByUsername(String username);

  boolean existsUserByEmail(String email);

  Optional<User> getUserByUsername(String username);

  UserDtoRegister save(UserDtoRegister userDto);

  UserDetailsService userDetailsService();

}