package com.db.javaschool.server;

public class Message {
    private long timeStamp;
    private String userName;
    private String message;

    public Message(long timeStamp, String userName, String message) {
        this.timeStamp = timeStamp;
        this.userName = userName;
        this.message = message;
    }
}
