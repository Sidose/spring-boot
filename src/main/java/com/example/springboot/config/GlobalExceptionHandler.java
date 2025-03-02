package com.example.springboot.config;

import com.example.springboot.exception.NoSuchItemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException exception) {
    Map<String,String> errors = new HashMap<>();
    exception.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }

  @ExceptionHandler({NoSuchItemException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Object> handleNoSuchItemException(
      NoSuchItemException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
  }

  @ExceptionHandler({RuntimeException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Object> handleRuntimeException(
      RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
  }

}
