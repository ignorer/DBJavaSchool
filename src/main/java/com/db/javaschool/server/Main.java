package com.db.javaschool.server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    /**
     * Accepts connection in endless loop
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Context context = new Context(new MessagePool());
        ServerSocket serverSocket = new ServerSocket(6666);
        ProtocolHandler handler = new ProtocolHandler(context);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            executorService.submit(() -> {
                try (DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());) {
                    while (true) {
                        handler.handle(inputStream.readUTF(), clientSocket);
                    }
                } catch (EOFException e) {
                } catch (SocketException e) {
                } catch (IOException e) {
                    executorService.shutdownNow();
                    e.printStackTrace();
                }
            });
        }
    }
}
