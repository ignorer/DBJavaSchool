package com.db.javaschool.server;

import com.db.javaschool.protocol.request.*;
import com.db.javaschool.server.exception.RequestParsingException;
import org.json.JSONObject;

public class RequestParser {
    private RequestParser() {
    }

    public static Request parseRequest(String s) {
        JSONObject json = new JSONObject(s);
        if (json.getString("type") == null) {
            throw new RequestParsingException("cannot determine type of request");
        }

        try {
            switch (json.getString("type")) {
                case "snd":
                    return new SendRequest(json);
                case "hist":
                    return new HistoryRequest(json);
                case "hist_info":
                    return new HistoryInfoRequest(json);
                case "connect":
                    return new ConnectRequest(json);
            }
        } catch (IllegalArgumentException e) {
            throw new RequestParsingException("some required data is missing or has invalid format");
        }
        throw new RequestParsingException("unknown type");
    }
}
