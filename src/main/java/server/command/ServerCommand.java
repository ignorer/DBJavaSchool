package server.command;

import server.Context;

public abstract class ServerCommand {
    protected Context context;
    public abstract void execute(String message);

    public ServerCommand(Context context) {
        this.context = context;
    }
}
