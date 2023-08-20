package com.aydintepe.panel.exception;

import jakarta.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GeneralExceptionAdvisor extends ResponseEntityExceptionHandler {

  @NotNull
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleDataIntegrityViolationException(
      DataIntegrityViolationException ex) {
    if (ex.getCause() instanceof ConstraintViolationException) {
      ConstraintViolationException constraintViolationException = (ConstraintViolationException) ex.getCause();
      String message = constraintViolationException.getSQLException().getMessage();
      Pattern pattern = Pattern.compile("\\((.*?)\\)");
      Matcher matcher = pattern.matcher(message);
      matcher.find();
      String key = matcher.group(1);
      matcher.find();
      String value = matcher.group(1);
      if (key.equals("username")) {
        message = "Bu isimde bir kullanıcı bulunmaktadır. Girilen kullanıcı ismi : " + value;
      }
      return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(ClientExistsWithSameEmailException.class)
  public ResponseEntity<?> handle(ClientExistsWithSameEmailException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<?> handle(UserNotFoundException exception) {
    return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
  }

  private ResponseEntity<?> handleNotFound(String s) {
    return new ResponseEntity<>(s, HttpStatus.NOT_FOUND);
  }
}