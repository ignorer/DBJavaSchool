package com.db.javaschool.server;

import org.json.JSONObject;

public class Message {
    private long timeStamp;
    private String userName;
    private String message;

    public Message(long timeStamp, String userName, String message) {
        this.timeStamp = timeStamp;
        this.userName = userName;
        this.message = message;
    }

    public Message(JSONObject jsonObject) {
        this.timeStamp = (long) jsonObject.get("timeStamp");
//        this.userName = (String) jsonObject.get("userName");
        this.message = (String) jsonObject.get("msg");
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
