package io.swagger.exceptions;

import org.springframework.http.HttpStatus;

public class ServerErrorException extends RestException {
    public ServerErrorException(String message) {
        super(message);
    }

    public ServerErrorException() {
        super("");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
