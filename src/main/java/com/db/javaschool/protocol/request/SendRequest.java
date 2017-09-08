package com.db.javaschool.protocol.request;

import org.json.JSONException;
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
        String type = "";

        try {
            type = json.getString("type");
            message = json.getString("message");
            token = json.getString("token");
        } catch (JSONException e) {
            throw new IllegalArgumentException("Wrong json format");
        }

        if (!type.equals("snd")) {
            throw new IllegalArgumentException("Wrong request type");
        }
    }

    @Override
    public String toJsonString() {
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
