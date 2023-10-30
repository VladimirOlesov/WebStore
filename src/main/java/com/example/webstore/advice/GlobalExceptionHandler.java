package com.example.webstore.advice;

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
  public ResponseEntity<ErrorResponseDto> handleDuplicateUserException(DuplicateUserException e) {
    ErrorResponseDto errorDto = new ErrorResponseDto(e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException e) {
    ErrorResponseDto errorDto = new ErrorResponseDto(e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorDto> handleValidationException(
      MethodArgumentNotValidException ex) {
    ValidationErrorDto validationError = new ValidationErrorDto();
    ex.getBindingResult().getFieldErrors().forEach(
        error -> validationError.addFieldError(error.getField(), error.getDefaultMessage()));

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
  }
}