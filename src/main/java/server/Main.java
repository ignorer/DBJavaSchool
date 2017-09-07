package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static private List<Socket> connections = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6667);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            connections.add(clientSocket);

            new Thread(() -> {
                try (DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                     DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream())) {
                        while (true) {
                            ProtocolHandler.handle(inputStream.readUTF());
                        }
                } catch (EOFException e) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
