package com.example.prography_quest.global.common.handler;

import com.example.prography_quest.global.common.exception.CustomException;
import com.example.prography_quest.global.common.handler.response.FieldErrorResponse;
import com.example.prography_quest.global.common.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> exceptionHandler(CustomException exception) {
        log.warn("CustomException = {}", exception);
        return ResponseEntity.status(exception.getExceptionCode().getStatus()).body(exception.getExceptionCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> validation(MethodArgumentNotValidException exception) {
        log.warn("MethodArgumentNotValidException = {}", exception);
        FieldErrorResponse fieldValidation = new FieldErrorResponse(exception);
        return ResponseEntity.badRequest().body(fieldValidation.getResponse());
    }
}
