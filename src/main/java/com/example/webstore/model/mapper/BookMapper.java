package com.example.webstore.model.mapper;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
    uses = {AuthorMapper.class, GenreMapper.class, AuthorRepositoryMapper.class,
        GenreRepositoryMapper.class})
public interface BookMapper {

  @Mapping(source = "author.authorId", target = "author", qualifiedBy = CustomMapping.class)
  @Mapping(source = "genre.genreId", target = "genre", qualifiedBy = CustomMapping.class)
  Book bookDtoToBook(BookDto bookDto);

  @Mapping(source = "id", target = "bookId")
  BookDto bookToBookDto(Book book);
}