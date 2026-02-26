package com._Comets.Hotel_service.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException{
    private  String message;
    private  HttpStatus status;

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public BadRequestException(String message) {
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
