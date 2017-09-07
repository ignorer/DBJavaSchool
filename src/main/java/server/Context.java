package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Context {
    private List<Socket> connections = new ArrayList<>();
    public MessagePool pool;

    Context(MessagePool pool) {
        this.pool = pool;
    }

    public void add(Socket socket) {
        connections.add(socket);
    }

    public void sendAll(String input) {
        connections.forEach(c -> {
            try (DataOutputStream outputStream = new DataOutputStream(c.getOutputStream())) {
                outputStream.writeUTF(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
