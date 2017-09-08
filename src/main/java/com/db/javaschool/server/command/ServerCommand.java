package com.db.javaschool.server.command;

import com.db.javaschool.protocol.request.Request;
import com.db.javaschool.server.Context;
import org.json.JSONObject;

public abstract class ServerCommand {
    protected Context context;
    public abstract void execute(Request request);

    public ServerCommand(Context context) {
        this.context = context;
    }
}
