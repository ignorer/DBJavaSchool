package com.db.javaschool.protocol.request;

public interface Request {
    RequestType getType();

    enum RequestType {
        CONNECT, HISTORY, HISTORY_INFO, SEND
    }
}
