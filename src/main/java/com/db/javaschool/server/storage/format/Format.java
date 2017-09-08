package com.db.javaschool.server.storage.format;

public interface Format<T> {
    String serialize(T data);

    T deserialize(String content);
}
