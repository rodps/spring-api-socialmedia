package com.rodrigo.socialmedia.controller;

import com.rodrigo.socialmedia.domain.ValidacaoException;
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

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity erroDeValidacaoDominio(ValidacaoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
