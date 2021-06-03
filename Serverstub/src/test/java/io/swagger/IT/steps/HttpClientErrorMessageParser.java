package io.swagger.IT.steps;

public class HttpClientErrorMessageParser {

    private String message;

    public HttpClientErrorMessageParser() {

    }

    public String parseMessage(String message) {
        System.out.println(message);
        String m = message.substring(message.indexOf("\"message\":") + 11, message.lastIndexOf(',') - 1);
        System.out.println(m);
        return m;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
