package com.db.javaschool.server;

import com.db.javaschool.server.entity.Message;
import com.db.javaschool.server.storage.Storage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MessagePool {
    private final static int MAX_NUMBER_OF_LOST_MESSAGES = 20;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final BlockingDeque<Message> messageQueue = new LinkedBlockingDeque<>();
    private final List<Message> cache = new ArrayList<>();
    private final Storage storage;
    private volatile int chunkCounter = 0;

    public MessagePool(Storage storage) throws IOException {
        this.storage = storage;
    }

    public Storage getStorage() {
        return storage;
    }

    public BlockingDeque<Message> getMessageQueue() {
        return messageQueue;
    }

    public List<Message> getCache() {
        return cache;
    }

    public void putMessageToDeque(@NotNull Message message) throws InterruptedException {

        messageQueue.putLast(message);

    }

    public Message getMessageFromDeque() throws IOException {
        Message message;
        try {
            message = messageQueue.takeFirst();
            putMessageToCache(message);
            return message;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted");
        }

    }

    public void putMessageToCache(@NotNull Message message) throws IOException {
        cache.add(message);
        if (cache.size() == MAX_NUMBER_OF_LOST_MESSAGES) {
            storage.store(cache);
        }
    }
}
