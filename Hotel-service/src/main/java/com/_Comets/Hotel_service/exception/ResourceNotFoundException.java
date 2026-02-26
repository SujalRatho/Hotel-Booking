package com._Comets.Hotel_service.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {
    private  String message;
    private HttpStatus status;

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ResourceNotFoundException(String message) {
        this.message = message;
        this.status = HttpStatus.NOT_FOUND;
    }
}
