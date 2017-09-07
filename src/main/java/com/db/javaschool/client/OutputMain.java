package com.db.javaschool.client;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class OutputMain {
    private String HOSTNAME = "127.0.0.1";
    private int PORT = 6666;

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
            StringBuilder stringBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputStr);
            }
            JSONObject json = new JSONObject(stringBuilder.toString());
            String jsonMessageType = (String) json.get("type");
            if (json.get("type") == "snd" ) {
                String message = (String) json.get("msg");
                outputMessages(message);
            }

    }


    public void outputMessages(String message) {
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
