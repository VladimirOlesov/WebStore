package com.example.webstore.model.mapper;

import com.example.webstore.model.dto.UserDtoRegister;
import com.example.webstore.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PasswordEncoderMapper.class)
public interface UserMapper {

  @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
  User userDtoToUser(UserDtoRegister userDto);

  @Mapping(target = "password", ignore = true)
  UserDtoRegister convertToDto(User user);
}