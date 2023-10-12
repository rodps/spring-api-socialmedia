package com.rodrigo.socialmedia.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity erroDeValidacaoDto(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(e -> new ErroDeValidacaoDTO(e.getField(), e.getDefaultMessage())));
    }
}
