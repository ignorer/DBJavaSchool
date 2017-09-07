package com.db.javaschool.client;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class InputMain {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("invalid number of arguments");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            try (Socket socket = new Socket("127.0.0.1", 6666);
                DataOutputStream stream = new DataOutputStream(socket.getOutputStream())) {
                while (true) {
                    String s = scanner.nextLine();
                    if (s.equals("exit")) {
                        return;
                    }

                    int firstSpace = s.indexOf(' ');
                    JSONObject json = new JSONObject();
                    json.put("type", s.substring(1, firstSpace));
                    json.put("token", args[0]);
                    if (s.startsWith("/snd")) {
                        json.put("msg", s.substring(firstSpace + 1));
                    } else if (s.startsWith("/hist")) {
                        json.put("pageNumber", s.substring(firstSpace + 1));
                    } else if (s.startsWith("/hist_info")) {
                    } else {
                        continue;
                    }
                    stream.writeUTF(json.toString());
                }
            } catch (IOException e) {
                return;
            }
        }
    }

    /**
     * Parse command into command name and arguments
     * @param input User input.
     * @return Array of strings where first element is command name and the others are arguments.
     */
    private static @NotNull String[] parseCommand(String input) {
        return new String[]{""};
    }
}
