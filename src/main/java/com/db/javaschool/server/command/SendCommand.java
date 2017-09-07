package com.db.javaschool.server.command;

import com.db.javaschool.server.Context;
import org.json.JSONObject;

public class SendCommand extends ServerCommand {
    public SendCommand(Context context) {
        super(context);
    }

    @Override
    public void execute(JSONObject message) {
        context.pool.addMessage(message);
        context.sendAll(message.get("msg").toString());
    }
}
