package com.db.javaschool.server.command;

import com.db.javaschool.protocol.request.Request;
import com.db.javaschool.protocol.request.SendRequest;
import com.db.javaschool.server.Context;
import org.json.JSONObject;

public class SendCommand extends ServerCommand {
    public SendCommand(Context context) {
        super(context);
    }

    @Override
    public void execute(Request request) {
    }
}
