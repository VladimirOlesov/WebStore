package com.example.webstore.model.mapper;

import com.example.webstore.model.dto.OrderInfoDto;
import com.example.webstore.model.entity.Order;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  Order orderDtoToOrder(OrderInfoDto orderDto);

  @Mapping(source = "id", target  = "orderId")
  @Mapping(source = "user.id", target = "userId")
  @Mapping(target = "bookId", expression = "java(bookId)")
  OrderInfoDto toOrderDto(Order order, @Context Long bookId);
}