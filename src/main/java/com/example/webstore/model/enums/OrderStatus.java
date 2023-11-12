package com.example.webstore.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
  IN_CART("В корзине"),
  COMPLETED("Завершен"),
  CANCELLED("Отменен");

  public final String displayValue;
}