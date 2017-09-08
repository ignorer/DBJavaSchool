package com.db.javaschool.server.entity;

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
        this.timeStamp = (long) jsonObject.get("timestamp");
        this.userName = (String) jsonObject.get("username");
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

    public String toJSON() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (timeStamp != message1.timeStamp) return false;
        if (userName != null ? !userName.equals(message1.userName) : message1.userName != null) return false;
        return message != null ? message.equals(message1.message) : message1.message == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (timeStamp ^ (timeStamp >>> 32));
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
