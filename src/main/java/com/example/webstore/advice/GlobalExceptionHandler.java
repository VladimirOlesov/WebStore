package com.example.webstore.advice;

import com.example.webstore.exception.BookCoverStorageException;
import com.example.webstore.exception.DuplicateUserException;
import com.example.webstore.model.dto.ErrorResponseDto;
import com.example.webstore.model.dto.ValidationErrorDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DuplicateUserException.class)
  public ResponseEntity<ErrorResponseDto> handleDuplicateUserException(
      DuplicateUserException e) {
    var errorResponseDto = new ErrorResponseDto(e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(
      EntityNotFoundException e) {
    var errorResponseDto = new ErrorResponseDto(e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(
      IllegalArgumentException e) {
    var errorResponseDto = new ErrorResponseDto(e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
  }

  @ExceptionHandler(BookCoverStorageException.class)
  public ResponseEntity<ErrorResponseDto> handleBookCoverStorageException(
      BookCoverStorageException e) {
    var errorResponseDto = new ErrorResponseDto(e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorDto> handleValidationException(
      MethodArgumentNotValidException ex) {
    var validationError = new ValidationErrorDto();
    ex.getBindingResult().getFieldErrors().forEach(
        error -> validationError.addFieldError(
            error.getField(), error.getDefaultMessage()));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
  }
}