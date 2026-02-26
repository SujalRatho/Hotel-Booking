package com._Comets.Hotel_service.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String message;
    private int status;

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    private LocalDateTime localDateTime;

    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.localDateTime = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
