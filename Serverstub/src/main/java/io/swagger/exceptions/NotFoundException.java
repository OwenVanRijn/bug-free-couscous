package io.swagger.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RestException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException() {
        super("");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
