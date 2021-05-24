package io.swagger.exceptions;

public class UnauthorisedException extends Exception {
    public UnauthorisedException(String message) {
        super(message);
    }

    public UnauthorisedException() {
        super("");
    }
}
