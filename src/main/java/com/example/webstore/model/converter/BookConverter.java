package com.example.webstore.model.converter;

import com.example.webstore.model.dto.BookDto;
import com.example.webstore.model.entity.Book;

public class BookConverter {

  public static BookDto convertToDto(Book book) {
    return BookDto.builder()
        .title(book.getTitle())
        .authorName(book.getAuthor().getAuthorName())
        .genreName(book.getGenre().getGenreName())
        .publicationYear(book.getPublicationYear())
        .price(book.getPrice())
        .ISBN(book.getISBN())
        .pageCount(book.getPageCount())
        .ageRating(book.getAgeRating())
        .build();
  }
}
