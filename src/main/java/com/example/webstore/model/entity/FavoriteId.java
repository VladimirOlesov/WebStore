package com.example.webstore.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class FavoriteId implements Serializable {

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "book_id", nullable = false)
  private Long bookId;
}










