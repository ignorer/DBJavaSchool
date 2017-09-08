package com.db.javaschool.protocol.request;

import org.json.JSONException;
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
        String type = "";

        try {
            type = json.getString("type");
            pageNumber = json.getInt("page");
            token = json.getString("token");
        } catch (JSONException e) {
            throw new IllegalArgumentException("Wrong json format");
        }

        if (!type.equals("hist")) {
            throw new IllegalArgumentException("Wrong request type");
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
