package com.example.webstore.model.mapper;

import com.example.webstore.model.dto.OrderDto;
import com.example.webstore.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  @Mapping(source = "id", target = "orderId")
  @Mapping(source = "user.id", target = "userId")
  OrderDto toOrderDto(Order order);
}