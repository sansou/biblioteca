package com.example.biblioteca.error;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    ErroResponse erroResponse = new ErroResponse("Erro ao precessar a requisição: valor inválido para o campo 'role'. Valores aceitos: 'ADMIN' ou 'USER'", 400);
    return ResponseEntity.badRequest().body(erroResponse);
  }
}
