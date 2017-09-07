package com.db.javaschool.client;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class InputMain {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("invalid number of arguments");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            try {
                Socket socket = new Socket("127.0.0.1", 6666);
                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream out = new DataOutputStream(outputStream);

                while (true) {
                    String s = scanner.next();
                    if (s.equals("exit")) {
                        return;
                    }

                    int firstSpace = s.indexOf(' ');
                    JSONObject json = new JSONObject();
                    json.put("type", s.substring(0, firstSpace));
                    json.put("token", args[0]);
                    if (s.startsWith("/snd")) {
                        json.put("msg", s.substring(firstSpace + 1));
                    } else if (s.startsWith("/hist")) {
                            json.put("pageNumber", s.substring(firstSpace + 1));
                    } else if (s.startsWith("/hist_info")) {
                    } else {
                        continue;
                    }
                    out.writeUTF(json.toString());
                }
            } catch (IOException e) {
                return;
            }
        }
    }
}
