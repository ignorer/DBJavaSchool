package com.db.javaschool.client;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class OutputMain {
    private String HOSTNAME = "127.0.0.1";
    private int PORT = 6667;

    public OutputMain() {
    }

    public OutputMain(String HOSTNAME, int PORT) {
        this.HOSTNAME = HOSTNAME;
        this.PORT = PORT;
    }

    private Socket openConnection() throws IOException {
        return new Socket(HOSTNAME, PORT);
    }

    private static BufferedReader getBufferedReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
    }

    public void readMessages(BufferedReader bufferedReader) throws IOException {
        String inputStr;
        while ((inputStr = bufferedReader.readLine()) != null) {
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

        if (args.length==0) {
            System.out.println("Invalid number of arguments");
            System.exit(1);
        }

        OutputMain outputMain = new OutputMain();
        BufferedReader bufferedReader = getBufferedReader(outputMain.openConnection());
        while (true) {
            outputMain.readMessages(bufferedReader);
        }

    }


}
