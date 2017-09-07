package server;

import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MessagePool {
    public final List<String> pool = new ArrayList<>(1000);

    public void addMessage(String message) {
        if (pool.size() >= 950) {
            dumpToFile();
        }
        synchronized (pool) {
            pool.add(message);
        }
    }

    public JSONObject getMessageChunk() {
        JSONObject answer;
        synchronized (pool) {
            answer = new JSONObject(pool);
        }
        return answer;
    }

    public void dumpToFile() {
        File file = new File("target", "test.txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            synchronized (pool) {
                pool.forEach(m -> {
                    try {
                        bw.write(m);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
