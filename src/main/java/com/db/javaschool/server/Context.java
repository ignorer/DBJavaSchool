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

    public MessagePool getPool() {
        return pool;
    }

    public User getUser(String token) {
        connections.get(token);
    }
}
