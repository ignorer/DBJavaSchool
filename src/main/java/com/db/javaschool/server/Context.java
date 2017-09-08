package com.db.javaschool.server;
import com.db.javaschool.server.entity.User;
import com.db.javaschool.server.storage.FileSystemStorage;

import java.io.File;
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

    public Context() throws IOException {
        this.pool = new MessagePool(new FileSystemStorage(new File("./storage")));
    }

    public Map<String, User> lockConnections() {
        connectionsLock.lock();
        return connections;
    }

    public void releaseConnections() {
        connectionsLock.unlock();
    }

    public MessagePool getPool() {
        return pool;
    }
}
