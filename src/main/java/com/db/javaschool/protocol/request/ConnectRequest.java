package com.db.javaschool.protocol.request;

import org.json.JSONException;
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
        String type = "";

        try {
            type = json.getString("type");
            username = json.getString("username");
            token = json.getString("token");
        } catch (JSONException e) {
            throw new IllegalArgumentException("Wrong json format");
        }

        if (!type.equals("connect")) {
            throw new IllegalArgumentException("Wrong request type");
        }
    }

    public String toJsonString() {
        return new JSONObject().
                put("type", "connect").
                put("username", username).
                put("token", token).
                toString();
    }

    @Override
    public RequestType getType() {
        return RequestType.CONNECT;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
