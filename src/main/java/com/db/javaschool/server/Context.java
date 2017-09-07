package com.db.javaschool.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Context {
    private Map<String, Socket> connections = new HashMap<>();
    public Map<String, Socket> getConnections() {
        return connections;
    }

    public MessagePool pool;

    Context(MessagePool pool) {
        this.pool = pool;
    }

    public void add(String token, Socket socket) {
        connections.put(token, socket);
    }

    public void sendAll(String input) {
        connections.forEach((k, v) -> {
            try (DataOutputStream outputStream = new DataOutputStream(v.getOutputStream())) {
                outputStream.writeUTF(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
