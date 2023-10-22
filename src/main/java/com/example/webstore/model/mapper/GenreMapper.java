package com.example.webstore.model.mapper;

import com.example.webstore.model.dto.GenreDto;
import com.example.webstore.model.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GenreMapper {

  @Mapping(target = "genreId", source = "id")
  GenreDto genreToDto(Genre genre);
}