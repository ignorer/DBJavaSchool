package server;

import org.json.JSONObject;
import server.command.HistoryCommand;
import server.command.HistoryInfoCommand;
import server.command.SendCommand;
import server.command.ServerCommand;

public class ProtocolHandler {
    private Context context;
    ProtocolHandler(Context context) {
        this.context = context;
    }

    public void handle(String stringInput) {
        JSONObject jsonInput = new JSONObject(stringInput);
        String commandType = (String) jsonInput.get("type");
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
