package com.example.webstore.service;

import com.example.webstore.model.dto.UserDtoLogin;
import com.example.webstore.model.dto.UserDtoRegister;

public interface AuthService {

  UserDtoRegister register(UserDtoRegister userDto);

  String authenticate(UserDtoLogin userDto);
}