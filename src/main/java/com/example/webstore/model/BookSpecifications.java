package com.example.webstore.model;

import com.example.webstore.model.entity.Author_;
import com.example.webstore.model.entity.Book;
import com.example.webstore.model.entity.Book_;

import com.example.webstore.model.entity.Genre_;
import com.example.webstore.model.enums.SortBy;
import com.example.webstore.model.enums.SortDirection;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {

  public static Specification<Book> titleContains(String title) {
    return (root, query, criteriaBuilder) -> {
      if (StringUtils.isEmpty(title)) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.like(root.get(Book_.title), "%"+ title + "%");
    };
  }

  public static Specification<Book> authorIs(Long authorId) {
    return (root, query, criteriaBuilder) -> {
      if (authorId == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get(Book_.author).get(Author_.Id), authorId);
    };
  }

  public static Specification<Book> genreIs(Long genreId) {
    return (root, query, criteriaBuilder) -> {
      if (genreId == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get(Book_.genre).get(Genre_.Id), genreId);
    };
  }

  public static Specification<Book> priceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
    return (root, query, criteriaBuilder) -> {
      if (minPrice == null && maxPrice == null) {
        return criteriaBuilder.conjunction();
      }
      if (minPrice != null && maxPrice != null) {
        return criteriaBuilder.between(root.get(Book_.price), minPrice, maxPrice);
      } else if (minPrice != null) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get(Book_.price), minPrice);
      } else {
        return criteriaBuilder.lessThanOrEqualTo(root.get(Book_.price), maxPrice);
      }
    };
  }

  public static Specification<Book> orderBy(SortBy sortBy, SortDirection sortDirection) {
    return (root, query, criteriaBuilder) -> {
      var order = criteriaBuilder.asc(root.get(Book_.title));

      switch (sortBy) {
        case TITLE -> order = criteriaBuilder.asc(root.get(Book_.title));
        case PUBLICATION_YEAR -> order = criteriaBuilder.asc(root.get(Book_.publicationYear));
        case PRICE -> {
          if (sortDirection == SortDirection.DESC) {
            order = criteriaBuilder.desc(root.get(Book_.price));
          } else {
            order = criteriaBuilder.asc(root.get(Book_.price));
          }
        }
      }

      query.orderBy(order);

      return query.getRestriction();
    };
  }
}