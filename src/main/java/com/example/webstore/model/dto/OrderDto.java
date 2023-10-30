package com.example.webstore.model.dto;

import com.example.webstore.model.enums.OrderStatus;
import java.sql.Timestamp;
import java.util.Set;
import lombok.Builder;

@Builder
public record OrderDto(
    Long orderId,
    Long userId,
    Timestamp orderDate,
    OrderStatus status,
    Set<Long> bookIds) {

}