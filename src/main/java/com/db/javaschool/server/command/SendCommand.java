package com.db.javaschool.server.command;

import com.db.javaschool.protocol.request.SendRequest;
import com.db.javaschool.server.Context;
import com.db.javaschool.server.entity.Message;
import com.db.javaschool.server.entity.User;

import java.util.Map;

public class SendCommand extends ServerCommand {
    public SendCommand(Context context) {
        super(context);
    }

    @Override
    public void execute(Object... objects) {
        SendRequest request = (SendRequest) objects[0];
        User user;
        try {
            Map<String, User> connections = context.lockConnections();
            user = connections.get(request.getToken());
        } finally {
            context.releaseConnections();
        }

        try {
            context.getPool().putMessageToDeque(new Message(0, user.getUsername(), request.getMessage()));
        } catch (InterruptedException e) {
            // ignore it
        }
    }
}
