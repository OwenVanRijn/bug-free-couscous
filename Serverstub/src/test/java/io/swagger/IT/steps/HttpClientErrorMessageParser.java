package io.swagger.IT.steps;

public class HttpClientErrorMessageParser {

    private String message;

    public HttpClientErrorMessageParser(String message) {
        this.message = ParseMessage(message);
    }

    private String ParseMessage(String message) {
        return message.substring(message.lastIndexOf("\"Not a valid"), message.lastIndexOf(','));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
