package com.example.webstore.model.mapper;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

  @Mapping(source = "author.authorName", target = "authorName")
  @Mapping(source = "genre.genreName", target = "genreName")
  BookDto bookToBookDto(Book book);
}

