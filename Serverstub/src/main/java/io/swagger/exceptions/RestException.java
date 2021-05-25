package io.swagger.exceptions;

import org.springframework.http.HttpStatus;

public abstract class RestException extends Exception {
    public RestException(String message) {
        super(message);
    }

    public RestException() {
        super("");
    }

    public abstract HttpStatus getStatus();
}
