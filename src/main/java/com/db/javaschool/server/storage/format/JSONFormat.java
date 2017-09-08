package com.db.javaschool.server.storage.format;

import org.json.JSONObject;

import java.util.List;

public class JSONFormat<T> implements Format<T> {
    @Override
    public String serialize(T data) {
        return new JSONObject(data).toString();
    }

    @Override
    public T deserialize(String content) {
        return null;
    }
}
