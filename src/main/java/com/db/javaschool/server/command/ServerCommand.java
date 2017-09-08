package com.db.javaschool.server.command;

import com.db.javaschool.server.Context;

public abstract class ServerCommand {
    protected Context context;

    public ServerCommand(Context context) {
        this.context = context;
    }

    public abstract void execute(Object... objects);
}
