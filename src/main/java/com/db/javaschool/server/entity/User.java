package com.db.javaschool.server.entity;

import java.io.DataOutputStream;
import java.net.Socket;

public class User {
    private String username;
    private DataOutputStream dataOutputStream;

    public User(String username, DataOutputStream dataOutputStream) {
        this.username = username;
        this.dataOutputStream = dataOutputStream;
    }

    public String getUsername() {
        return username;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }
}
