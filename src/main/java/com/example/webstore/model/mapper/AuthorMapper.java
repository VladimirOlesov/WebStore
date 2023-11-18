package com.example.webstore.model.mapper;

import com.example.webstore.model.dto.AuthorDto;
import com.example.webstore.model.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

  @Mapping(source = "id", target  = "authorId")
  AuthorDto authorToDto(Author author);

  Author authorDtoToAuthor(AuthorDto authorDto);
}