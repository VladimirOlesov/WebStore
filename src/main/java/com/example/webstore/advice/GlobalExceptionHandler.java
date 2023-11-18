package com.example.webstore.advice;

import com.example.webstore.exception.BookCoverException;
import com.example.webstore.exception.BookExportException;
import com.example.webstore.exception.DuplicateException;
import com.example.webstore.model.dto.ErrorResponseDto;
import com.example.webstore.model.dto.ValidationErrorDto;
import jakarta.persistence.EntityNotFoundException;
import java.io.FileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DuplicateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleDuplicateUserException(DuplicateException e) {
    return new ErrorResponseDto(e.getMessage());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ErrorResponseDto handleEntityNotFoundException(EntityNotFoundException e) {
    return new ErrorResponseDto(e.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponseDto handleIllegalArgumentException(IllegalArgumentException e) {
    return new ErrorResponseDto(e.getMessage());
  }

  @ExceptionHandler(BookCoverException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponseDto handleBookCoverStorageException(BookCoverException e) {
    return new ErrorResponseDto(e.getMessage());
  }

  @ExceptionHandler(BookExportException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponseDto handleBookExportException(BookExportException e) {
    return new ErrorResponseDto(e.getMessage());
  }

  @ExceptionHandler(FileNotFoundException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponseDto handleFileNotFoundException(FileNotFoundException e) {
    return new ErrorResponseDto(e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ValidationErrorDto handleValidationException(MethodArgumentNotValidException ex) {
    ValidationErrorDto validationError = new ValidationErrorDto();
    ex.getBindingResult().getFieldErrors().forEach(
        error -> validationError.addFieldError(
            error.getField(), error.getDefaultMessage()));
    return validationError;
  }
}