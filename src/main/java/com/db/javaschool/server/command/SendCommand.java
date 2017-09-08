package com.db.javaschool.server.command;

import com.db.javaschool.protocol.request.Request;
import com.db.javaschool.protocol.request.SendRequest;
import com.db.javaschool.server.Context;
import com.db.javaschool.server.entity.Message;
import com.db.javaschool.server.entity.User;
import org.json.JSONObject;

public class SendCommand extends ServerCommand {
    public SendCommand(Context context) {
        super(context);
    }

    @Override
    public void execute(Object... objects) {
        SendRequest request = (SendRequest) objects[0];
        User user = context.getUser(request.getToken());

        try {
            context.getPool().putMessage(new Message(0, user.getUsername(), request.getMessage()));
        } catch (InterruptedException e) {
            // ignore it
        }
    }
}
