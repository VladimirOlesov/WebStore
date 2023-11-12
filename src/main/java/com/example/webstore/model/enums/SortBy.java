package com.example.webstore.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortBy {
  TITLE("title"),
  PRICE("price"),
  PUBLICATION_YEAR("publicationYear");

  private final String field;
}