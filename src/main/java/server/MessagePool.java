package server;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessagePool {
    List<String> pool = new ArrayList<>(1000);

    public void addMessage(String message) {
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
}
