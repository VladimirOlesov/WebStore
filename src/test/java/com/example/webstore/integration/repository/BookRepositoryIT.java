package com.example.webstore.integration.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.webstore.integration.IntegrationTestBase;
import com.example.webstore.model.entity.Author;
import com.example.webstore.model.entity.Book;
import com.example.webstore.model.entity.Genre;
import com.example.webstore.repository.AuthorRepository;
import com.example.webstore.repository.BookRepository;
import com.example.webstore.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class BookRepositoryIT extends IntegrationTestBase {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final GenreRepository genreRepository;

  public static final String ID = "id";
  public static final String TITLE = "title";
  public static final String AUTHOR = "author";
  public static final String GENRE = "genre";
  public static final String ISBN = "ISBN";

  /**
   * Тестирование сохранения книги в базе данных. Ожидается, что сохраненная книга не является null,
   * имеет идентификатор и соответствует переданным данным.
   */
  @Test
  void testSaveBook() {

    Author author = authorRepository.save(
        Author.builder()
            .authorName(AUTHOR)
            .build()
    );

    Genre genre = genreRepository.save(
        Genre.builder()
            .genreName(GENRE)
            .build()
    );

    Book book = Book.builder()
        .title(TITLE)
        .author(author)
        .genre(genre)
        .ISBN(ISBN)
        .isDeleted(false)
        .build();

    Book savedBook = bookRepository.save(book);

    assertThat(savedBook)
        .isNotNull()
        .hasFieldOrProperty(ID)
        .isEqualTo(book);
  }
}