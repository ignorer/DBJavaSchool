package com.db.javaschool.client;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class OutputMain {
    private Socket socket;

    public OutputMain(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) throws IOException {

        OutputMain outputMain = new OutputMain(new Socket("127.0.0.1", 6666));
        outputMain.displayMessages();

    }

    public void displayMessages() throws IOException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

        while (true) {
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            JSONObject json = new JSONObject(responseStrBuilder.toString());
            String jsonMessageType = (String) json.get("type");
            if (json.get("type") == "snd" ) {
                System.out.println(json.get("msg"));
            }

        }

    }


}
