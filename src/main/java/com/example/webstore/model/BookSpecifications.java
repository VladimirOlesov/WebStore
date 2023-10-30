package com.example.webstore.model;

import com.example.webstore.model.entity.Book;
import com.example.webstore.model.enums.SortDirection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {

  public static Specification<Book> titleContains(String title) {
    return (root, query, criteriaBuilder) -> {
      if (StringUtils.isEmpty(title)) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.like(root.get("title"), "%" + title + "%");
    };
  }

  public static Specification<Book> authorIs(Long authorId) {
    return (root, query, criteriaBuilder) -> {
      if (authorId == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get("author").get("id"), authorId);
    };
  }

  public static Specification<Book> genreIs(Long genreId) {
    return (root, query, criteriaBuilder) -> {
      if (genreId == null) {
        return criteriaBuilder.conjunction();
      }
      return criteriaBuilder.equal(root.get("genre").get("id"), genreId);
    };
  }

  public static Specification<Book> priceBetween(Double minPrice, Double maxPrice) {
    return (root, query, criteriaBuilder) -> {
      if (minPrice == null && maxPrice == null) {
        return criteriaBuilder.conjunction();
      }
      if (minPrice != null && maxPrice != null) {
        return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
      } else if (minPrice != null) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
      } else {
        return criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
      }
    };
  }

  public static Specification<Book> orderByPrice(SortDirection sortDirection) {
    return (root, query, criteriaBuilder) -> {
      if (sortDirection == SortDirection.ASC) {
        query.orderBy(criteriaBuilder.asc(root.get("price")));
      } else {
        query.orderBy(criteriaBuilder.desc(root.get("price")));
      }
      return query.getRestriction();
    };
  }

  public static Specification<Book> orderByPublicationYear(SortDirection sortDirection) {
    {
      return (root, query, criteriaBuilder) -> {
        if (sortDirection == SortDirection.ASC) {
          query.orderBy(criteriaBuilder.asc(root.get("publicationYear")));
        }
        return query.getRestriction();
      };
    }
  }

  public static Specification<Book> orderByTitle() {
    return (root, query, criteriaBuilder) -> {
      query.orderBy(criteriaBuilder.asc(root.get("title")));
      return query.getRestriction();
    };
  }
}