package com.sujal.api_gateway.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(
            BadRequestException ex
    ) {
        ErrorResponse error =
                new ErrorResponse(
                        ex.getStatus().value(),
                        ex.getMessage()
                );

        return ResponseEntity.status(ex.getStatus()).body(error);
    }
}