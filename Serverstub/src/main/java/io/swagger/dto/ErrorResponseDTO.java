package io.swagger.dto;

import io.swagger.exceptions.RestException;

public class ErrorResponseDTO {
    private String message;

    public ErrorResponseDTO(RestException e){
        message = e.getMessage();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
