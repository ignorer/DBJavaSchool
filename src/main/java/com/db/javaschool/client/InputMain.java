package com.db.javaschool.client;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class InputMain {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            try {
                Socket socket = new Socket("127.0.0.1", 6666);
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream out = new DataOutputStream(outputStream);

                while (true) {
                    String s = scanner.next();
                    if (s.equals("exit")) {
                        return;
                    }

                    int firstSpace = s.indexOf('c');
                    JSONObject json = new JSONObject();
                    json.put("type", s.substring(0, firstSpace));
                    if (s.startsWith("snd")) {
                        json.put("msg", s.substring(firstSpace + 1));
                    }
                    if (s.startsWith("hist")) {
                            json.put("msg", Integer.parseInt(s.substring(firstSpace + 1).trim()));
                    }
                    out.writeUTF(json.toString());
                }
            } catch (IOException e) {
                return;
            }
        }
    }
}
