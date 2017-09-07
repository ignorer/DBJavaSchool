package com.db.javaschool.server;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MessagePool {
    private volatile int chunkCounter = 0;
    public final List<Message> pool = new ArrayList<>(1000);
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

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
            dumpToFile();
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

    public void dumpToFile() {
        FileHandler fileHandler = new FileHandler("target");
        fileHandler.dumpFile(toJson());
    }

    public JSONObject toJson() {
        return new JSONObject().put("history", pool);
    }
}
