package com.db.javaschool.server;

import com.db.javaschool.server.storage.FileSystemStorage;
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
        storage = new FileSystemStorage("./storage");
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
        storage.store(cache);
    }

        //////////////////////////////////////////////////////////////////////////
    public void addMessage(Message message) {
        lock.writeLock().lock();
        cache.add(message);
        if (cache.size() == 1000) {

            cache.clear();
        }
        lock.writeLock().unlock();
    }

    public void addMessage(JSONObject message) {
        lock.writeLock().lock();
        cache.add(new Message(message));
        if (cache.size() == 1000) {
            dumpToFile(cache);
            cache.clear();
        }
        lock.writeLock().unlock();
    }



    public JSONObject getMessageChunk() {
        JSONObject answer;

        lock.readLock().lock();
            answer = new JSONObject(cache);
        lock.readLock().unlock();

        return answer;
    }

    public void dumpToFile() {
        FileHandler fileHandler = new FileHandler("target");
        try {
            fileHandler.dumpFile(toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJson() {
        return new JSONObject().put("history", cache);
    }
}
