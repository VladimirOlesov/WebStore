package com.example.webstore.model.entity;

import com.example.webstore.model.enums.OrderStatus;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AttributeOverride(name = "Id", column = @Column(name = "order_id"))
@Table(name = "orders")
public class Order extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "order_date")
  private Timestamp orderDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private OrderStatus status;
}

