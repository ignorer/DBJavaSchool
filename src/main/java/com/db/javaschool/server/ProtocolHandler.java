package com.db.javaschool.server;

import org.json.JSONObject;
import com.db.javaschool.server.command.HistoryCommand;
import com.db.javaschool.server.command.HistoryInfoCommand;
import com.db.javaschool.server.command.SendCommand;
import com.db.javaschool.server.command.ServerCommand;

import java.net.Socket;

public class ProtocolHandler {
    private Context context;
    ProtocolHandler(Context context) {
        this.context = context;
    }

    public void handle(String stringInput, Socket socket) {
        JSONObject jsonInput = new JSONObject(stringInput);
        String commandType = (String) jsonInput.get("type");
        context.add((String) jsonInput.get("token"), socket);
        //parse json

        ServerCommand command;

        switch (commandType) {
            case "hist_info":
                command = new HistoryInfoCommand(context);
                break;
            case "hist":
                command = new HistoryCommand(context);
                break;
            case "send":
                command = new SendCommand(context);
                break;
            default:
                System.out.println("Unsupported command: " + commandType);
                return;
        }

        command.execute((String) jsonInput.get("msg"));
    }
}
