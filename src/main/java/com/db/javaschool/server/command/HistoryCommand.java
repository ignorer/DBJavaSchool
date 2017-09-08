package com.db.javaschool.server.command;

import com.db.javaschool.protocol.request.Request;
import com.db.javaschool.server.Context;
import org.json.JSONObject;

public class HistoryCommand extends ServerCommand{
    public HistoryCommand(Context context) {
        super(context);
    }

    @Override
    public void execute(Request request) {

    }
}
