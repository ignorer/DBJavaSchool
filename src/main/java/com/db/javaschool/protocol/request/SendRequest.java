package com.db.javaschool.protocol.request;

import org.json.JSONObject;

import java.util.IllegalFormatException;

public class SendRequest implements Request {
    private final String msg;
    private final String token;

    public SendRequest(String msg, String token) {
        this.msg = msg;
        this.token = token;
    }

    public SendRequest(JSONObject json) throws IllegalFormatException {
        String type = json.getString("type");
        if (type == null || !type.equals("msg")) {
            throw new IllegalArgumentException("wrong request type");
        }
        msg = json.getString("msg");
        token = json.getString("token");
        if (token == null || msg == null) {
            throw new IllegalArgumentException("wrong json format");
        }
    }

    @Override
    public String toString() {
        return new JSONObject().
            put("type", "snd").
            put("msg", msg).
            put("token", token).
            toString();
    }
}
