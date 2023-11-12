package com.example.webstore.exception;

public class DuplicateException extends RuntimeException {

  public DuplicateException(String message) {
    super(message);
  }
}