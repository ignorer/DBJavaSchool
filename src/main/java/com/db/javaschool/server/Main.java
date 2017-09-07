package com.db.javaschool.server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        Context context = new Context(new MessagePool());
        ServerSocket serverSocket = new ServerSocket(6666);
        ProtocolHandler handler = new ProtocolHandler(context);


        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> {
                try (DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());) {
                        while (true) {
                            handler.handle(inputStream.readUTF(), clientSocket);
                        }
                } catch (EOFException e) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
