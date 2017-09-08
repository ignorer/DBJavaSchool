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
     */
    public static void main(String[] args) throws IOException {


//        Context context = new Context(new MessagePool());
//        ServerSocket inputSocket = new ServerSocket(6666);
////        ServerSocket outputSocket = new ServerSocket(6667);
//        ProtocolHandler handler = new ProtocolHandler(context);
//
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//
//        while (true) {
//            Socket clientSocket = inputSocket.accept();
////            Socket clientSocket2 = outputSocket.accept();
//            executorService.submit(() -> {
//                DataInputStream inputStream = null;
//                try {
//                    inputStream = new DataInputStream(clientSocket.getInputStream());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    while (true) {
//                        handler.handle(inputStream.readUTF(), clientSocket);
//                    }
//                } catch (EOFException e) {
//                    while (true) {
//                        try {
//                            handler.handle(inputStream.readUTF(), clientSocket);
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
//                    }
//                } catch (SocketException e) {
//                } catch (IOException e) {
//                    executorService.shutdownNow();
//                    e.printStackTrace();
//                }
//            });
//        }


    }
}
