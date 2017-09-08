package com.db.javaschool.server;

import com.db.javaschool.server.entity.Message;
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

    public MessagePool(FileSystemStorage storage) throws IOException {
        this.storage = storage;
    }

    public void putMessage(@NotNull Message message) throws InterruptedException {
        try {
            lock.writeLock().lock();
            messageQueue.putLast(message);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Message getMessage() throws IOException {
        try {
            Message message;
            lock.writeLock().lock();
            message = messageQueue.pollFirst();
            archiveMessage(message);
            return message;
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void archiveMessage(@NotNull Message message) throws IOException {
        cache.add(message);
        if (cache.size() == MAX_NUMBER_OF_LOST_MESSAGES) {
            storage.store(cache);
        }
    }
}
