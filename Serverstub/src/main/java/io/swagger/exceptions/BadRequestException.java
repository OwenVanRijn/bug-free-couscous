package io.swagger.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RestException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
        super("");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
