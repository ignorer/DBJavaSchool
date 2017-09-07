package com.db.javaschool.server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MessagePool {
    private volatile int chunkCounter = 0;
    public final List<Message> pool = new ArrayList<>(1000);
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public void addMessage(Message message) {
        rwl.writeLock().lock();
        if (pool.size() >= 950) {
            dumpToFile();
            pool.clear();
        }
        pool.add(message);
        rwl.writeLock().unlock();
    }

    public void addMessage(JSONObject message) {

        rwl.writeLock().lock();
        if (pool.size() >= 950) {
            dumpToFile();
            pool.clear();
        }
        pool.add(new Message(message));
        rwl.writeLock().unlock();
    }



    public JSONObject getMessageChunk() {
        JSONObject answer;

        rwl.readLock().lock();
            answer = new JSONObject(pool);
        rwl.readLock().unlock();

        return answer;
    }

    public void dumpToFile() {
        FileHandler fileHandler = new FileHandler("target");
        fileHandler.dumpFile(toJson());
    }

    public String toJsonString() {
        return new JSONObject().put("history", pool).toString();
    }

    public JSONObject toJson() {
        return new JSONObject().put("history", pool);
    }
}
