package com.example.webstore.service;

import com.example.webstore.model.dto.UserDtoRegister;
import com.example.webstore.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {

  User getUserByUsername(String username);

  UserDtoRegister save(UserDtoRegister userDto);

  UserDetailsService userDetailsService();

  User getAuthenticatedUser();
}