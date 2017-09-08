package com.db.javaschool.server;
import com.db.javaschool.server.entity.User;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class Context {
    private Map<String, User> connections = new HashMap<>();
    private ReentrantLock connectionsLock = new ReentrantLock();
    private MessagePool pool;

//    public Map<String, User> getConnections() {
//        return connections;
//    }

    public Context() throws IOException {
        this.pool = new MessagePool();
    }

    public void add(String token, String username, Socket socket) {
        connections.put(token, new User(username, socket));
    }

    public Collection<User> lockConnections() {
        connectionsLock.lock();
        return connections.values();
    }

    public void releaseConnections() {
        connectionsLock.unlock();
    }

//    public void sendAll(String input) {
//        connections.forEach((k, v) -> {
//            try (DataOutputStream outputStream = new DataOutputStream( v.getSocket().getOutputStream())) {
//                JSONObject json = new JSONObject();
//                json.put("type", "snd");
//                json.put("msg", input);
//                outputStream.writeUTF(json.toString());
//                System.out.println(json.toString());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//    }

    public MessagePool getPool() {
        return pool;
    }
}
