package com.example.webstore.model.mapper;

import com.example.webstore.model.dto.OrderDto;
import com.example.webstore.model.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  Order orderDtoToOrder(OrderDto orderDto);

}