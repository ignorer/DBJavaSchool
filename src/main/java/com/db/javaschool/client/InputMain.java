package com.db.javaschool.client;

import com.db.javaschool.protocol.request.HistoryInfoRequest;
import com.db.javaschool.protocol.request.HistoryRequest;
import com.db.javaschool.protocol.request.Request;
import com.db.javaschool.protocol.request.SendRequest;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class InputMain {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("invalid number of arguments");
            return;
        }

        try (Socket serverSocket = new Socket("127.0.0.1", 6666);
             Socket outputConsoleSocket = new Socket("127.0.0.1", 6667);
             DataInputStream inputStremFromServer = new DataInputStream(serverSocket.getInputStream());
             DataOutputStream outputStreamToServer = new DataOutputStream(serverSocket.getOutputStream());
             DataOutputStream outputStreamToOutputConsole = new DataOutputStream(outputConsoleSocket.getOutputStream());
        )
        {
            new Thread(() -> {
                while (true) {
                    try {
                        outputStreamToOutputConsole.writeUTF(inputStremFromServer.readUTF());
                    } catch (IOException e) {
                        throw new RuntimeException("I have no purpose to live anymore");
                    }
                }

            }).start();


            Scanner scanner = new Scanner(System.in);
            while (true) {
                String s = scanner.nextLine();
                if (s.equals("/exit")) {
                    return;
                }

                try {
                    String[] tokens = parseCommand(s);
                    Request request = buildRequest(tokens, args[0]);
                        outputStreamToServer.writeUTF(request.toString());
                } catch (IllegalArgumentException e) {
                    // ignore it
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("");
        }


    }

    /**
     * Parse command into command name and arguments
     *
     * @param input User input.
     * @return Array of strings where first element is command name and the others are arguments.
     */
    private static @NotNull String[] parseCommand(@NotNull String input) {
        if (!input.startsWith("/")) {
            throw new IllegalArgumentException("invalid format");
        }
        input = input.substring(1, input.length());

        int firstSpace = input.indexOf(' ');
        if (firstSpace == -1) {
            return new String[]{input};
        }
        String commandName = input.substring(0, firstSpace);
        switch (commandName) {
            case "snd":
                return new String[]{commandName, input.substring(firstSpace + 1, input.length())};
            case "hist_info":
                return new String[]{commandName};
            case "hist":
                List<String> tokens = Arrays.stream(input.substring(firstSpace + 1, input.length()).split(" "))
                    .filter(token -> !token.equals(""))
                    .collect(Collectors.toList());
                if (tokens.size() != 1) {
                    throw new IllegalArgumentException("invalid number of arguments");
                }
                return new String[]{commandName, tokens.get(0)};
            default:
                throw new IllegalArgumentException("unknown command");
        }
    }

    private static @NotNull Request buildRequest(String[] tokens, String authToken) {
        switch (tokens[0]) {
            case "snd":
                return new SendRequest(tokens[1], authToken);
            case "hist_info":
                return new HistoryInfoRequest(authToken);
            case "hist":
                return new HistoryRequest(authToken, Integer.parseInt(tokens[1]));
            default:
                throw new IllegalArgumentException("invalid command name");
        }
    }
}
