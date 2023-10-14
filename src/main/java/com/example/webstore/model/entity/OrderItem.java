package com.example.webstore.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

  @EmbeddedId
  private OrderItemId id;

  @ManyToOne
  @JoinColumn(name = "book_id", insertable = false, updatable = false)
  private Book book;

  @ManyToOne
  @JoinColumn(name = "order_id", insertable = false, updatable = false)
  private Order order;

  @Column(name = "price", nullable = false)
  private BigDecimal price;
}
