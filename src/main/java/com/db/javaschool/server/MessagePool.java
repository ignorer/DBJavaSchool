package com.db.javaschool.server;

import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MessagePool {
    private volatile int chunkCounter = 0;
    public final List<String> pool = new ArrayList<>(1000);
    final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public void addMessage(String message) {

        rwl.writeLock().lock();
        if (pool.size() >= 950) {
            dumpToFile();
            pool.clear();
        }
        pool.add(message);
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
        File file = new File("target", chunkCounter + "test.txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {

            rwl.readLock().lock();
            pool.forEach(m -> {
                try {
                    bw.write(m);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            rwl.readLock().unlock();

            chunkCounter++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
