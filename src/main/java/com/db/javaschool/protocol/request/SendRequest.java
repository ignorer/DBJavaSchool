package com.db.javaschool.protocol.request;

import org.json.JSONObject;

import java.util.IllegalFormatException;

public class SendRequest implements Request {
    private final String message;
    private final String token;

    public SendRequest(String message, String token) {
        this.message = message;
        this.token = token;
    }

    public SendRequest(JSONObject json) throws IllegalFormatException {
        String type = json.getString("type");
        if (type == null || !type.equals("message")) {
            throw new IllegalArgumentException("wrong request type");
        }
        message = json.getString("message");
        token = json.getString("token");
        if (token == null || message == null) {
            throw new IllegalArgumentException("wrong json format");
        }
    }

    @Override
    public String toString() {
        return new JSONObject().
            put("type", "snd").
            put("message", message).
            put("token", token).
            toString();
    }

    @Override
    public RequestType getType() {
        return RequestType.SEND;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
