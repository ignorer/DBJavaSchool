package com.db.javaschool.client;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class OutputMain {
    private final int PORT = 6667;
    private Socket socket;
    private DataInputStream dataInputStream;

    public OutputMain() throws IOException {
        startListener();

    }


    private void startListener() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        socket = serverSocket.accept();
        setInputStream();
    }

    private void setInputStream() throws IOException {
        dataInputStream = new DataInputStream(socket.getInputStream());
    }

    public void readMessageFlow() throws IOException {

        while (true)  {
            String inputStr = dataInputStream.readUTF();
            JSONObject json = new JSONObject(inputStr);

            if (!json.has("type")) {
                System.out.println("No type key in json message");
                return;
            }
            String jsonMessageType = (String) json.get("type");

            if ("msg".equals(jsonMessageType) ) {
                if (!json.has("message")) {
                    System.out.println("No message");
                    return;
                }
                String message = (String) json.get("message");
                outputMessage(message);

            } else if ("hist_info".equals(jsonMessageType)) {
                // TO DO
            } else if ("hist".equals(jsonMessageType)) {
                if (json.has("messages")) {
                      String messages = (String) json.get("messages");
                }

            } else if ("error".equals(jsonMessageType)) {
                // TO DO
            } else {
                throw new RuntimeException("Unknown message type");
            }
        }

    }

    public void outputMessage(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) throws IOException {

        OutputMain outputMain = new OutputMain();
        outputMain.readMessageFlow();


    }


}
