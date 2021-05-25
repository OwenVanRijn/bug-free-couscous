package io.swagger.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorisedException extends RestException {
    public UnauthorisedException(String message) {
        super(message);
    }

    public UnauthorisedException() {
        super("");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
