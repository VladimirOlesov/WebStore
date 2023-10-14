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
public class OrderItemId implements Serializable {

  @Column(name = "book_id", nullable = false)
  private Long bookId;

  @Column(name = "order_id", nullable = false)
  private Long orderId;
}
