package com.example.webstore.model.mapper;

import com.example.webstore.model.dto.AuthorDto;
import com.example.webstore.model.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

  @Mapping(target  = "authorId", source = "id")
  AuthorDto authorToDto(Author author);
}