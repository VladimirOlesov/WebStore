package com.example.webstore.model.dto;

import com.example.webstore.model.entity.Book;
import com.example.webstore.model.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;

@Builder
public record OrderDto(
    Long orderId,
    Long userId,
    LocalDateTime orderDate,
    OrderStatus status,
    Set<Book> books) {
}