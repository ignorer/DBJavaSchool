package server.command;

import server.Context;

public abstract class ServerCommand {
    private Context context;
    public abstract void execute();

    public ServerCommand(Context context) {
        this.context = context;
    }
}
