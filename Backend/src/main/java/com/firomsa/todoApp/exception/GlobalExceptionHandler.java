package com.firomsa.todoApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.firomsa.todoApp.dto.ResponseDTO;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<ResponseDTO<Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {

        ResponseDTO<Object> response = ResponseDTO.builder()
                .message(exception.getMessage())
                .status(false)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({ BadCredentialsException.class })
    public ResponseEntity<ResponseDTO<Object>> handleBadCredentialsException(
            BadCredentialsException exception) {

        ResponseDTO<Object> response = ResponseDTO.builder()
                .message(exception.getMessage())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler({ AuthException.class })
    public ResponseEntity<ResponseDTO<Object>> handleAuthException(
            AuthException exception) {

        ResponseDTO<Object> response = ResponseDTO.builder()
                .message(exception.getMessage())
                .status(false)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({ ResourceNotFoundException.class })
    public ResponseEntity<ResponseDTO<Object>> handleResourceNotFoundException(
            ResourceNotFoundException exception) {

        ResponseDTO<Object> response = ResponseDTO.builder()
                .message(exception.getMessage())
                .status(false)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({ UserNameAlreadyExistsException.class })
    public ResponseEntity<ResponseDTO<Object>> handleUserNameAlreadyExistsException(
            UserNameAlreadyExistsException exception) {

        ResponseDTO<Object> response = ResponseDTO.builder()
                .message(exception.getMessage())
                .status(false)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ResponseDTO<Object>> handleException(
            Exception exception) {

        log.warn(exception.getMessage());
        ResponseDTO<Object> response = ResponseDTO.builder()
                .message("An internal server error occurred")
                .status(false)
                .build();

        return ResponseEntity.internalServerError().body(response);
    }
}