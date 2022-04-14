package br.com.alura.aluraflixapi.dto;

import org.springframework.http.HttpStatus;

public class ExceptionDto {
    private final HttpStatus status;
    private final String message;

    public ExceptionDto(HttpStatus status, String error) {
        this.status = status;
        this.message = error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
