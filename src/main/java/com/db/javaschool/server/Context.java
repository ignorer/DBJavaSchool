package com.db.javaschool.server;
import com.db.javaschool.server.entity.User;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Context {
    private Map<String, User> connections = new HashMap<>();
    private MessagePool pool;

    public Map<String, User> getConnections() {
        return connections;
    }

    Context(MessagePool pool) {
        this.pool = pool;
    }

    public void add(String token, Socket socket) {
        connections.put(token, new User());
    }

    public void sendAll(String input) {
        connections.forEach((k, v) -> {
            try (DataOutputStream outputStream = new DataOutputStream( v.getSocket().getOutputStream())) {
                JSONObject json = new JSONObject();
                json.put("type", "snd");
                json.put("msg", input);
                outputStream.writeUTF(json.toString());
                System.out.println(json.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public MessagePool getPool() {
        return pool;
    }
}
