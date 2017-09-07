package com.db.javaschool.protocol.request;

import org.json.JSONObject;

import java.util.IllegalFormatException;

public class ConnectRequest implements Request {
    private final String username;
    private final String token;

    public ConnectRequest(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public ConnectRequest(JSONObject json) throws IllegalFormatException {
        String type = json.getString("type");
        if (type == null || !type.equals("username")) {
            throw new IllegalArgumentException("wrong request type");
        }
        username = json.getString("username");
        token = json.getString("token");
        if (token == null || username == null) {
            throw new IllegalArgumentException("wrong json format");
        }
    }

    @Override
    public String toString() {
        return new JSONObject().
            put("type", "snd").
            put("username", username).
            put("token", token).
            toString();
    }
}
