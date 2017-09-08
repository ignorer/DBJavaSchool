package com.db.javaschool.server;

import com.db.javaschool.server.storage.FilesystemStorage;
import com.db.javaschool.server.storage.Storage;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MessagePool {
    private final static int MAX_NUMBER_OF_LOST_MESSAGES = 20;

    private volatile int chunkCounter = 0;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final BlockingDeque<Message> messageQueue = new LinkedBlockingDeque<>();
    private final List<Message> cache = new ArrayList<>();
    private final Storage storage;

    public MessagePool() throws IOException {
        storage = new FilesystemStorage("./storage");
    }

    public void putMessage(@NotNull Message message) throws InterruptedException {
        try {
            lock.writeLock().lock();
            messageQueue.putLast(message);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void getMessage() {
        Message message;
        try {
            lock.readLock().lock();
            message = messageQueue.pollFirst();
            archiveMessage(message);
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void archiveMessage(@NotNull Message message) {
        cache.add(message);
        if (cache.size() == MAX_NUMBER_OF_LOST_MESSAGES) {
            dumpToFile(cache);
        }
    }

    public void dumpToFile(List<Message> cache) {
        storage.dump();
    }

        //////////////////////////////////////////////////////////////////////////
    public void addMessage(Message message) {
        lock.writeLock().lock();
        pool.add(message);
        if (pool.size() == 1000) {

        }
        lock.writeLock().unlock();
    }

    public void addMessage(JSONObject message) {

        lock.writeLock().lock();
        if (pool.size() >= 950) {
            dumpToFile(cache);
            pool.clear();
        }
        pool.add(new Message(message));
        lock.writeLock().unlock();
    }



    public JSONObject getMessageChunk() {
        JSONObject answer;

        lock.readLock().lock();
            answer = new JSONObject(pool);
        lock.readLock().unlock();

        return answer;
    }

    public JSONObject toJson() {
        return new JSONObject().put("history", pool);
    }
}
