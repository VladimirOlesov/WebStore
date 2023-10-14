package com.example.webstore.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
  IN_CART("В корзине"),
  AWAIT_PAYMENT("Ожидает оплаты"),
  COMPLETED("Завершен");

  public final String displayValue;
}
