package com.example.webstore.model.mapper;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
    uses = {AuthorMapper.class, GenreMapper.class})
public interface BookMapper {

  Book bookDtoToBook(BookDto bookDto);

  BookDto bookToBookDto(Book book);
}