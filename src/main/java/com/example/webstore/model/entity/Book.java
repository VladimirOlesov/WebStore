package com.example.webstore.model.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Builder
@FieldNameConstants
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AttributeOverride(name = "Id", column = @Column(name = "book_id"))
@Table(name = "books")
public class Book extends BaseEntity {

  @Column(name = "title", nullable = false)
  private String title;

  @NotNull
  @ManyToOne (cascade = CascadeType.PERSIST)
  @JoinColumn(name = "author_id", nullable = false)
  private Author author;

  @NotNull
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "genre_id", nullable = false)
  private Genre genre;

  @Column(name = "publication_year")
  private Integer publicationYear;

  @Column(name = "price", precision = 8, scale = 2)
  private BigDecimal price;

  @Column(name = "ISBN", nullable = false, unique = true)
  private String ISBN;

  @Column(name = "page_count")
  private Integer pageCount;

  @Column(name = "age_rating")
  private Integer ageRating;

  @Column(name = "cover_path")
  private String coverPath;

  @Column(name = "deleted", nullable = false)
  private Boolean deleted;
}