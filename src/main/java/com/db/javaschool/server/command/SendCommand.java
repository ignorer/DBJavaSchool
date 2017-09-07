package com.db.javaschool.server.command;

import com.db.javaschool.server.Context;

public class SendCommand extends ServerCommand {
    public SendCommand(Context context) {
        super(context);
    }

    @Override
    public void execute(String message) {
        context.pool.addMessage(message);
        context.sendAll(message);
    }
}
