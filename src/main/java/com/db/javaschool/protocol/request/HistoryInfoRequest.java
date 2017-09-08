package com.db.javaschool.protocol.request;

import org.json.JSONObject;

import java.util.IllegalFormatException;

public class HistoryInfoRequest implements Request {
    private final String token;

    public HistoryInfoRequest(String token) {
        this.token = token;
    }

    public HistoryInfoRequest(JSONObject json) throws IllegalFormatException {
        String type = json.getString("type");
        if (type == null || !type.equals("hist_info")) {
            throw new IllegalArgumentException("wrong request type");
        }
        token = json.getString("token");
        if (token == null) {
            throw new IllegalArgumentException("wrong json format");
        }
    }

    @Override
    public String toJsonString() {
        return new JSONObject().
            put("type", "hist_info").
            put("token", token).
            toString();
    }

    @Override
    public RequestType getType() {
        return RequestType.HISTORY_INFO;
    }

    public String getToken() {
        return token;
    }
}
