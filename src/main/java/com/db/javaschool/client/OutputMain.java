package com.db.javaschool.client;

import com.db.javaschool.server.exception.RequestParsingException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.EOFException;
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

    private static void outputMessage(String message) {
        System.out.println(message);
    }

    public static void main(String[] args) throws IOException {
        OutputMain outputMain = new OutputMain();
        outputMain.readMessageFlow();
    }

    private void startListener() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            socket = serverSocket.accept();
            setInputStream();
        } catch (IOException e) {
            throw new RuntimeException("Failed to start server on port " + PORT);
        }

    }

    private void setInputStream() throws IOException {
        dataInputStream = new DataInputStream(socket.getInputStream());
    }

    public void readMessageFlow() {

        while (true) {
            String inputStr = null;
            try {
                inputStr = dataInputStream.readUTF();
            } catch (EOFException e) {
                // Do nothing
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
//            outputMessage(inputStr);
            JSONObject json = new JSONObject(inputStr);
            System.out.println(json.get("username") + " says: " + json.get("message"));

//            if (json.getString("type") == null) {
//                throw new RequestParsingException("cannot determine type of request", inputStr);
//            }
//            try {
//                switch (json.getString("type")) {
//                    case "snd":
//                        outputMessage(json.getString("message"));
//                        break;
//                    case "hist":
//
//                    case "hist_info":
//
//                    case "connect":
//                }
//            } catch (IllegalArgumentException | JSONException e) {
//                throw new RequestParsingException("some required data is missing or has invalid format", inputStr);
//            }
//            throw new RequestParsingException("unknown type", inputStr);
        }
    }


}
