package com.db.javaschool.protocol.request;

import org.json.JSONObject;

import java.util.IllegalFormatException;

public class HistoryRequest implements Request {
    private final String token;
    private final int pageNumber;

    public HistoryRequest(String token, int page) {
        this.token = token;
        this.pageNumber = page;
    }

    public HistoryRequest(JSONObject json) throws IllegalFormatException {
        String type = json.getString("type");
        if (type == null || !type.equals("hist")) {
            throw new IllegalArgumentException("wrong request type");
        }
        token = json.getString("token");
        try {
            pageNumber = Integer.parseInt(json.getString("page"));
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("wrong page number");
        }
        if (token == null) {
            throw new IllegalArgumentException("wrong json format");
        }
    }

    @Override
    public String toJsonString() {
        return new JSONObject().
            put("type", "hist").
            put("page", Integer.toString(pageNumber)).
            put("token", token).
            toString();
    }

    @Override
    public RequestType getType() {
        return RequestType.HISTORY;
    }

    public String getToken() {
        return token;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
