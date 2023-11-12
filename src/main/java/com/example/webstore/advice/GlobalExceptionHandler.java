package com.example.webstore.advice;

import com.example.webstore.exception.BookCoverException;
import com.example.webstore.exception.DuplicateException;
import com.example.webstore.model.dto.ErrorResponseDto;
import com.example.webstore.model.dto.ValidationErrorDto;
import jakarta.persistence.EntityNotFoundException;
import java.io.FileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DuplicateException.class)
  public ResponseEntity<ErrorResponseDto> handleDuplicateUserException(
      DuplicateException e) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(
      EntityNotFoundException e) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(
      IllegalArgumentException e) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
  }

  @ExceptionHandler(BookCoverException.class)
  public ResponseEntity<ErrorResponseDto> handleBookCoverStorageException(
      BookCoverException e) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
  }

  @ExceptionHandler(FileNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleFileNotFoundException(
      FileNotFoundException e) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorDto> handleValidationException(
      MethodArgumentNotValidException ex) {
    ValidationErrorDto validationError = new ValidationErrorDto();
    ex.getBindingResult().getFieldErrors().forEach(
        error -> validationError.addFieldError(
            error.getField(), error.getDefaultMessage()));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
  }
}