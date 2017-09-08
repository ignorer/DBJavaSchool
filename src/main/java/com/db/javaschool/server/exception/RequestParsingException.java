package com.db.javaschool.server.exception;

public class RequestParsingException extends IllegalArgumentException {
    private final String requestText;

    public RequestParsingException(String message, String request) {
        super(message);
        this.requestText = request;
    }

    public String getRequestText() {
        return requestText;
    }
}
